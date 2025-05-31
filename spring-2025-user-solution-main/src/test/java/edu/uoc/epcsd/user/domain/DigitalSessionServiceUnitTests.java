package edu.uoc.epcsd.user.domain;

import static edu.uoc.epcsd.user.util.TestConstants.NON_EXISTING_USER_ID;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_DESCRIPTION;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_ID;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_LINK;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_LOCATION;
import static edu.uoc.epcsd.user.util.TestConstants.USER_EMAIL;
import static edu.uoc.epcsd.user.util.TestConstants.USER_FULL_NAME;
import static edu.uoc.epcsd.user.util.TestConstants.USER_ID;
import static edu.uoc.epcsd.user.util.TestConstants.USER_PASSWORD;
import static edu.uoc.epcsd.user.util.TestConstants.USER_PHONE_NUMBER;
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
public class DigitalSessionServiceUnitTests {


  @Mock
  private DigitalSessionRepository digitalSessionRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private DigitalSessionServiceImpl digitalSessionService;

  @Test
  public void FindDigitalSessionByUserTest_ExistingUser() {
    User mockUser = User.builder().id(USER_ID).fullName(USER_FULL_NAME).email(USER_EMAIL).password(USER_PASSWORD)
        .phoneNumber(USER_PHONE_NUMBER).build();

    DigitalSession mockDigitalSession = DigitalSession.builder().id(SESSION_ID).userId(mockUser.getId())
        .description(SESSION_DESCRIPTION).location(SESSION_LOCATION).link(SESSION_LINK).build();

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
    when(userRepository.findUserById(NON_EXISTING_USER_ID)).thenReturn(Optional.empty());

    ThrowableAssert.ThrowingCallable callable = () -> digitalSessionService.findDigitalSessionByUser(
        NON_EXISTING_USER_ID);

    assertThatThrownBy(callable).isInstanceOf(UserNotFoundException.class);
    verify(userRepository).findUserById(NON_EXISTING_USER_ID);
    verify(digitalSessionRepository, never()).findDigitalSessionByUser(NON_EXISTING_USER_ID);
  }
}
