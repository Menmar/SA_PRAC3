package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> {

  List<CategoryEntity> findCategoryEntityByNameContaining(String name);

  List<CategoryEntity> findCategoryEntityByDescriptionContaining(String description);

}
