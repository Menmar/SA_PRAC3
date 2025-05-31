package edu.uoc.epcsd.notification.application.rest.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class GetProductResponse {

  private Long id;

  private String name;

  private String description;

  private Double dailyPrice;

  private String brand;

  private String model;

  private Long categoryId;

}
