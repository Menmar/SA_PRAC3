package edu.uoc.epcsd.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class DigitalItemUnitTests {

  private static final Long TEST_ITEM_ID = 1L;
  private static final Long TEST_SESSION_ID = 1L;
  private static final String TEST_DESCRIPTION = "Test description";
  private static final Long TEST_LATITUDE = 1L;
  private static final Long TEST_LONGITUDE = 1L;
  private static final String TEST_LINK = "https://item.com/test";

  @Test
  public void StatusAfterItemCreationTest() {
    DigitalItem digitalItem = DigitalItem.builder().id(TEST_ITEM_ID).digitalSessionId(TEST_SESSION_ID)
        .description(TEST_DESCRIPTION).lat(TEST_LATITUDE).lon(TEST_LONGITUDE).link(TEST_LINK).build();

    assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE);

  }
}
