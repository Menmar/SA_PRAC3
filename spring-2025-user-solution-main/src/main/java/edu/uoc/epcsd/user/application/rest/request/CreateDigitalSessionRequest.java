package edu.uoc.epcsd.user.application.rest.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CreateDigitalSessionRequest {

  @NotNull
  private final Long userId;

  @NotBlank
  private final String description;

  @NotBlank
  private final String location;

  @NotBlank
  private final String link;
}
