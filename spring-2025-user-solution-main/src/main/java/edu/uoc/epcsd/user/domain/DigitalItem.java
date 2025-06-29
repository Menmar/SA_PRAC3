package edu.uoc.epcsd.user.domain;

import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
public class DigitalItem {

  @NotNull
  private Long id;

  @NotNull
  private Long digitalSessionId;

  @NotNull
  private String description;

  @NotNull
  private Long lat;

  @NotNull
  private Long lon;

  @NotNull
  private String link;

  @NotNull
  @Builder.Default
  private DigitalItemStatus status = DigitalItemStatus.AVAILABLE;

}
