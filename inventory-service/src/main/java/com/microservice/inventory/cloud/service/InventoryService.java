package com.microservice.inventory.cloud.service;

import com.microservice.inventory.cloud.model.Inventory;
import com.microservice.inventory.cloud.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean inStockProduct(String productCode){
        Optional<Inventory> entity = inventoryRepository.findByProductCode(productCode);
        if(entity.isPresent() && entity.get().getQuantity() > 0){
            return true;
        }
        return false;
    }


    public Inventory findByProductCode(String productCode) {
        return inventoryRepository.findByProductCode(productCode).get();
    }
}
