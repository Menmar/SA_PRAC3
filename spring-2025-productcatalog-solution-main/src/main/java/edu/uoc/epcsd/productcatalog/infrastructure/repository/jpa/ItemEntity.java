package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import edu.uoc.epcsd.productcatalog.domain.Item;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Item")
@ToString(exclude = "product")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity implements DomainTranslatable<Item> {

  @Id
  @Column(name = "serialNumber", nullable = false, unique = true)
  private String serialNumber;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private ItemStatus status = ItemStatus.OPERATIONAL;

  @ManyToOne
  private ProductEntity product;

  public static ItemEntity fromDomain(Item item) {
    if (item == null) {
      return null;
    }

    return ItemEntity.builder()
        .serialNumber(item.getSerialNumber())
        .status(ItemStatus.fromDomain(item.getStatus()))
        .build();
  }

  @Override
  public Item toDomain() {
    return Item.builder()
        .serialNumber(this.getSerialNumber())
        .status(ItemStatus.toDomain(this.getStatus()))
        .productId(this.getProduct().getId())
        .build();
  }
}
