package edu.uoc.epcsd.user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DigitalItemUnitTest {

  @Test
  public void textStatusAfterItemCreation() {
    DigitalItem digitalItem = DigitalItem.builder().id(1L).digitalSessionId(1L).description("Test description").lat(1L)
        .lon(1L).link("http://item.com/test").build();

    Assertions.assertEquals(DigitalItemStatus.AVAILABLE, digitalItem.getStatus());

  }
}
