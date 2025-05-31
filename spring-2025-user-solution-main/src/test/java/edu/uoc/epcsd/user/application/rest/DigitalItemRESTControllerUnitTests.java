package edu.uoc.epcsd.user.application.rest;

import static edu.uoc.epcsd.user.util.TestConstants.TEST_DESCRIPTION;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_ITEM_ID;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LATITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LINK;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_LONGITUDE;
import static edu.uoc.epcsd.user.util.TestConstants.TEST_SESSION_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.service.DigitalItemService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DigitalItemRESTController.class)
public class DigitalItemRESTControllerUnitTests {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DigitalItemService digitalItemService;

  @Test
  public void findDigitalItemBySessionTest() throws Exception {
    Long sessionId = 2L;
    DigitalItem item = DigitalItem.builder().id(TEST_ITEM_ID).digitalSessionId(TEST_SESSION_ID)
        .description(TEST_DESCRIPTION).lat(TEST_LATITUDE).lon(TEST_LONGITUDE).link(TEST_LINK).build();

    List<DigitalItem> expectedItems = Collections.singletonList(item);

    when(digitalItemService.findDigitalItemBySession(sessionId)).thenReturn(expectedItems);

    mockMvc.perform(get("/digitalItem/digitalItemBySession").param("digitalSessionId", sessionId.toString()))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(item.getId().intValue())))
        .andExpect(jsonPath("$[0].description", is(item.getDescription())))
        .andExpect(jsonPath("$[0].digitalSessionId", is(item.getDigitalSessionId().intValue())));
  }
}
