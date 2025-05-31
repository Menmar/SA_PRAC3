package edu.uoc.epcsd.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
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
public class Alert {

  @NotNull
  private Long id;

  @NotNull
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate from;

  @NotNull
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate to;

  @NotNull
  private Long productId;

  @NotNull
  private Long userId;

}
