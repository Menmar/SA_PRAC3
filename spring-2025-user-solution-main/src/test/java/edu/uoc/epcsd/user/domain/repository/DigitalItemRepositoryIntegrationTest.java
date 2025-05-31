package edu.uoc.epcsd.user.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.User;
import edu.uoc.epcsd.user.infrastructure.repository.jpa.DigitalItemRepositoryImpl;
import edu.uoc.epcsd.user.infrastructure.repository.jpa.DigitalSessionRepositoryImpl;
import edu.uoc.epcsd.user.infrastructure.repository.jpa.UserRepositoryImpl;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DigitalItemRepositoryImpl.class, DigitalSessionRepositoryImpl.class, UserRepositoryImpl.class})
@ActiveProfiles("test")
public class DigitalItemRepositoryIntegrationTest {

  @Autowired
  private DigitalItemRepository digitalItemRepository;
  @Autowired
  private DigitalSessionRepository digitalSessionRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  public void findDigitalItemBySessionTest() {
    User user = User.builder().fullName("Integration Test User").email("integration@test.com").password("password")
        .phoneNumber("123456789").build();

    Long userId = userRepository.createUser(user);
    User createdUser = userRepository.findUserById(userId).orElseThrow();

    DigitalSession session = DigitalSession.builder().userId(createdUser.getId()).description("Test Session")
        .location("Integration Test Session").link("http://item.com/test").build();

    Long sessionId = digitalSessionRepository.createDigitalSession(session);
    DigitalSession createdSession = digitalSessionRepository.getDigitalSessionById(sessionId).orElseThrow();

    DigitalItem item1 = DigitalItem.builder().digitalSessionId(createdSession.getId()).description("Item 1").lat(10L)
        .lon(20L).link("http://item.com/test/item1").build();
    digitalItemRepository.createDigitalItem(item1);
    DigitalItem item2 = DigitalItem.builder().digitalSessionId(createdSession.getId()).description("Item 2").lat(11L)
        .lon(21L).link("http://item.com/test/item2").build();
    digitalItemRepository.createDigitalItem(item2);

    List<DigitalItem> retrievedItems = digitalItemRepository.findDigitalItemBySession(createdSession.getId());
    assertThat(retrievedItems).isNotNull();
    assertThat(retrievedItems.size()).isEqualTo(2);
    assertThat(retrievedItems).extracting(DigitalItem::getDescription).containsExactlyInAnyOrder("Item 1", "Item 2");
    assertThat(retrievedItems).extracting(DigitalItem::getDigitalSessionId)
        .allMatch(id -> id.equals(createdSession.getId()));
  }
}
