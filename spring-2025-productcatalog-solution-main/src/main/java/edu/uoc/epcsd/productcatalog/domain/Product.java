package edu.uoc.epcsd.productcatalog.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends CatalogElement {

  @NotNull
  private Double dailyPrice;

  @NotNull
  private String brand;

  @NotNull
  private String model;

  @NotNull
  private Long categoryId;

}
