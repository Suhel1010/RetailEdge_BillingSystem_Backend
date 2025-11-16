package org.billing.repository;

import org.billing.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity,Long> {

    Optional<ItemEntity> findByItemId(String id);
    Integer countByCategoryEntity_Id(Long id);

}
