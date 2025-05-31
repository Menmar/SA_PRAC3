package edu.uoc.epcsd.user.infrastructure.repository.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uoc.epcsd.user.domain.Alert;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Alert")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertEntity implements DomainTranslatable<Alert> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`from`", nullable = false, columnDefinition = "DATE")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate from;

  @Column(name = "`to`", nullable = false, columnDefinition = "DATE")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate to;

  @Column(name = "productId", nullable = false)
  private Long productId;

  @ManyToOne
  private UserEntity user;

  public static AlertEntity fromDomain(Alert alert) {
    if (alert == null) {
      return null;
    }

    return AlertEntity.builder()
        .id(alert.getId())
        .from(alert.getFrom())
        .to(alert.getTo())
        .productId(alert.getProductId())
        .build();
  }

  @Override
  public Alert toDomain() {
    return Alert.builder()
        .id(this.getId())
        .from(this.getFrom())
        .to(this.getTo())
        .productId(this.getProductId())
        .userId(this.getUser().getId())
        .build();
  }
}
