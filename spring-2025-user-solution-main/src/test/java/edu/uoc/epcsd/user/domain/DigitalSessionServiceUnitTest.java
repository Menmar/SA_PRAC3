package edu.uoc.epcsd.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.uoc.epcsd.user.domain.exception.UserNotFoundException;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import edu.uoc.epcsd.user.domain.repository.UserRepository;
import edu.uoc.epcsd.user.domain.service.DigitalSessionServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DigitalSessionServiceUnitTest {

  @Mock
  private DigitalSessionRepository digitalSessionRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private DigitalSessionServiceImpl digitalSessionService;

  @Test
  public void FindDigitalSessionByUserTest_ExistingUser() {
    User mockUser = User.builder().id(1L).fullName("Test User").email("test@example.com").password("password")
        .phoneNumber("123456789").build();
    DigitalSession mockDigitalSession = DigitalSession.builder().id(2L).userId(mockUser.getId())
        .description("Test Digital Session").location("Test Location").link("https://example.com").build();

    List<DigitalSession> expectedSessions = Collections.singletonList(mockDigitalSession);

    when(userRepository.findUserById(mockUser.getId())).thenReturn(Optional.of(mockUser));
    when(digitalSessionRepository.findDigitalSessionByUser(mockUser.getId())).thenReturn(expectedSessions);

    List<DigitalSession> actualSessions = digitalSessionService.findDigitalSessionByUser(mockUser.getId());

    assertThat(actualSessions).isEqualTo(expectedSessions);
    verify(userRepository).findUserById(mockUser.getId());
    verify(digitalSessionRepository).findDigitalSessionByUser(mockUser.getId());
  }

  @Test
  public void FindDigitalSessionByUserTest_NonExistingUser() {
    when(userRepository.findUserById(1L)).thenReturn(Optional.empty());

    ThrowableAssert.ThrowingCallable callable = () -> digitalSessionService.findDigitalSessionByUser(1L);

    assertThatThrownBy(callable).isInstanceOf(UserNotFoundException.class);
    verify(userRepository).findUserById(1L);
    verify(digitalSessionRepository, never()).findDigitalSessionByUser(1L);
  }
}
