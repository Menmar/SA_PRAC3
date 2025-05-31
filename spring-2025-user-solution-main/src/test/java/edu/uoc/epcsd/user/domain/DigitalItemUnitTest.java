package edu.uoc.epcsd.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class DigitalItemUnitTest {

  @Test
  public void textStatusAfterItemCreation() {
    DigitalItem digitalItem = DigitalItem.builder().id(1L).digitalSessionId(1L).description("Test description").lat(1L)
        .lon(1L).link("http://item.com/test").build();

    assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE);

  }
}
