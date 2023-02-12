package com.microservice.inventory.cloud.repository;

import com.microservice.inventory.cloud.model.Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    Optional<Inventory> findByProductCode(String productCode);

    @Query("Select i from Inventory i where i.quantity = 0")
    List<Inventory> findInventoryUnavailable();
}
