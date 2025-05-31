package edu.uoc.epcsd.user.application.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.User;
import edu.uoc.epcsd.user.domain.repository.DigitalItemRepository;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import edu.uoc.epcsd.user.domain.repository.UserRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DigitalItemRESTControllerIntegrationTests {


  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private DigitalItemRepository digitalItemRepository;
  @Autowired
  private DigitalSessionRepository digitalSessionRepository;
  @Autowired
  private UserRepository userRepository;

  private String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = "http://localhost:" + port;

  }

  @Test
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

    String url = baseUrl + "/digitalItem/digitalItemBySession?digitalSessionId=" + createdSession.getId();
    ResponseEntity<List<DigitalItem>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
        new ParameterizedTypeReference<>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<DigitalItem> retrievedItems = responseEntity.getBody();
    Assertions.assertThat(retrievedItems).isNotNull();
    Assertions.assertThat(retrievedItems.size()).isEqualTo(2);
    Assertions.assertThat(retrievedItems).extracting(DigitalItem::getDescription)
        .containsExactlyInAnyOrder("Item 1", "Item 2");
    Assertions.assertThat(retrievedItems).extracting(DigitalItem::getDigitalSessionId)
        .allMatch(id -> id.equals(createdSession.getId()));
  }
}
