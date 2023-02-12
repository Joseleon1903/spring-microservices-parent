package com.microservice.inventory.cloud.repository;

import com.microservice.inventory.cloud.model.InventoryCheck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryCheckRepository extends CrudRepository<InventoryCheck, Long> {

//    @Query(value = "Select i from t_inventory_check i order by i.last_check desc LIMIT 1", nativeQuery = true)
    Optional<InventoryCheck> findFirstByOrderByLastCheckDesc();

}
