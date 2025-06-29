package edu.uoc.epcsd.productcatalog.application.rest.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CreateItemRequest {

  @NotNull
  private final Long productId;

  @NotBlank
  private final String serialNumber;

}
