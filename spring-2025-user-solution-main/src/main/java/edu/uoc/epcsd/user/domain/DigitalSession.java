package edu.uoc.epcsd.user.domain;

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
public class DigitalSession {

  @NotNull
  private Long id;

  @NotNull
  private String description;
  @NotNull

  private String location;

  @NotNull
  private String link;

  @NotNull
  private Long userId;

}
