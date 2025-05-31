package edu.uoc.epcsd.user.domain;

import static edu.uoc.epcsd.user.util.TestConstants.TEST_DESCRIPTION;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_ITEM_ID;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LATITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LINK;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LONGITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_SESSION_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class DigitalItemUnitTests {


  @Test
  public void StatusAfterItemCreationTest() {
    DigitalItem digitalItem = DigitalItem.builder().id(TEST_ITEM_ID).digitalSessionId(TEST_SESSION_ID)
        .description(TEST_DESCRIPTION).lat(TEST_LATITUDE).lon(TEST_LONGITUDE).link(TEST_LINK).build();

    assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE);

  }
}
