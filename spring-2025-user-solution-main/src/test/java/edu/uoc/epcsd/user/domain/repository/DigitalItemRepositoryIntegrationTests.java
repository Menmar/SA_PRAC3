package edu.uoc.epcsd.user.domain.repository;

import static edu.uoc.epcsd.user.util.TestConstants.SESSION_DESCRIPTION;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_LINK;
import static edu.uoc.epcsd.user.util.TestConstants.SESSION_LOCATION;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_DESCRIPTION;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_DESCRIPTION2;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LATITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LATITUDE2;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LINK;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LINK2;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LONGITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LONGITUDE2;
import static edu.uoc.epcsd.user.util.TestConstants.USER_EMAIL;
import static edu.uoc.epcsd.user.util.TestConstants.USER_FULL_NAME;
import static edu.uoc.epcsd.user.util.TestConstants.USER_PASSWORD;
import static edu.uoc.epcsd.user.util.TestConstants.USER_PHONE_NUMBER;
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
public class DigitalItemRepositoryIntegrationTests {

  @Autowired
  private DigitalItemRepository digitalItemRepository;
  @Autowired
  private DigitalSessionRepository digitalSessionRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  public void findDigitalItemBySessionTest() {
    User user = User.builder().fullName(USER_FULL_NAME).email(USER_EMAIL).password(USER_PASSWORD)
        .phoneNumber(USER_PHONE_NUMBER).build();

    Long userId = userRepository.createUser(user);
    User createdUser = userRepository.findUserById(userId).orElseThrow();

    DigitalSession session = DigitalSession.builder().userId(createdUser.getId()).description(SESSION_DESCRIPTION)
        .location(SESSION_LOCATION).link(SESSION_LINK).build();

    Long sessionId = digitalSessionRepository.createDigitalSession(session);
    DigitalSession createdSession = digitalSessionRepository.getDigitalSessionById(sessionId).orElseThrow();

    DigitalItem item1 = DigitalItem.builder().digitalSessionId(createdSession.getId()).description(TEST_DESCRIPTION)
        .lat(TEST_LATITUDE)
        .lon(TEST_LONGITUDE).link(TEST_LINK).build();
    digitalItemRepository.createDigitalItem(item1);
    DigitalItem item2 = DigitalItem.builder().digitalSessionId(createdSession.getId()).description(TEST_DESCRIPTION2)
        .lat(TEST_LATITUDE2)
        .lon(TEST_LONGITUDE2).link(TEST_LINK2).build();
    digitalItemRepository.createDigitalItem(item2);

    List<DigitalItem> retrievedItems = digitalItemRepository.findDigitalItemBySession(createdSession.getId());
    assertThat(retrievedItems).isNotNull();
    assertThat(retrievedItems.size()).isEqualTo(2);
    assertThat(retrievedItems).extracting(DigitalItem::getDescription)
        .containsExactlyInAnyOrder(TEST_DESCRIPTION, TEST_DESCRIPTION2);
    assertThat(retrievedItems).extracting(DigitalItem::getDigitalSessionId)
        .allMatch(id -> id.equals(createdSession.getId()));
  }
}
