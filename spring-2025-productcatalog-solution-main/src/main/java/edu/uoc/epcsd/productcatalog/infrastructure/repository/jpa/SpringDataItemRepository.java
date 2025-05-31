package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataItemRepository extends JpaRepository<ItemEntity, String> {

  Optional<ItemEntity> findItemEntityBySerialNumber(String serialNumber);

  List<ItemEntity> findItemEntitiesByProduct(ProductEntity productEntity);

}
