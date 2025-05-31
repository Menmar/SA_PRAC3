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
public class User {

  @NotNull
  private Long id;

  @NotNull
  private String fullName;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String phoneNumber;

}
