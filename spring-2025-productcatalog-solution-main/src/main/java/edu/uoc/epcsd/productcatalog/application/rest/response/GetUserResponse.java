package edu.uoc.epcsd.productcatalog.application.rest.response;

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
public class GetUserResponse {

  private Long id;

  private String fullName;

  private String email;

  private String phoneNumber;

}

