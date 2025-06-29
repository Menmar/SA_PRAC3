package edu.uoc.epcsd.user.application.rest.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CreateDigitalItemRequest {

  @NotNull
  private final Long digitalSessionId;

  @NotBlank
  private final String description;

  @NotNull
  private final Long lat;

  @NotNull
  private final Long lon;

  @NotBlank
  private final String link;
}
