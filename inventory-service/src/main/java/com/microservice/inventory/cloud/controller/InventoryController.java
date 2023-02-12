package com.microservice.inventory.cloud.controller;

import com.microservice.inventory.cloud.model.Inventory;
import com.microservice.inventory.cloud.record.InStockResponse;
import com.microservice.inventory.cloud.repository.InventoryCheckRepository;
import com.microservice.inventory.cloud.service.InventoryCheckService;
import com.microservice.inventory.cloud.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/v0.1/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryCheckService inventoryCheckService;

    @Autowired
    public InventoryController(InventoryService inventoryService, InventoryCheckService inventoryCheckService) {
        this.inventoryService = inventoryService;
        this.inventoryCheckService = inventoryCheckService;
    }

    @GetMapping("/{productCode}")
    @ResponseBody
    public ResponseEntity<InStockResponse> inStock(@PathVariable("productCode") String productCode){

        inventoryCheckService.checkInventory();

        InStockResponse reponse = new InStockResponse(inventoryService.inStockProduct(productCode)) ;
        return new ResponseEntity(reponse, HttpStatus.OK);
    }

    @GetMapping("/find/{productCode}")
    @ResponseBody
    public ResponseEntity<Inventory> findByProductCode(@PathVariable("productCode") String productCode){
        Inventory response = inventoryService.findByProductCode(productCode);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity<Void> checkStock(){
        inventoryCheckService.checkInventory();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/check")
    @ResponseBody
    public ResponseEntity<Void> deleteStockUnavailable(){
        inventoryCheckService.checkUnavailableInventory();
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Inventory> updateStockAvailable(@RequestParam("productCode") String productCode, @RequestParam("quantity") Integer quantity){
        Inventory inventory = inventoryCheckService.checkAvailableInventory(productCode,quantity);
        return new ResponseEntity(inventory, HttpStatus.OK);
    }

}
