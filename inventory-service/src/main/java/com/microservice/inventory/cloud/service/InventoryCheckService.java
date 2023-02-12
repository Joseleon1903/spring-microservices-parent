package com.microservice.inventory.cloud.service;

import com.microservice.inventory.cloud.model.Inventory;
import com.microservice.inventory.cloud.model.InventoryCheck;
import com.microservice.inventory.cloud.record.ProductDTO;
import com.microservice.inventory.cloud.repository.InventoryCheckRepository;
import com.microservice.inventory.cloud.repository.InventoryRepository;
import com.microservice.inventory.cloud.util.InventoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryCheckService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckService.class);

    private final InventoryCheckRepository inventoryCheckRepository;

    private final WebClient.Builder webClientBuilder;

    private static int inMinuteUpdate = 5;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryCheckService(InventoryCheckRepository inventoryCheckRepository, WebClient.Builder webClient,
                                 InventoryRepository inventoryRepository) {
        this.inventoryCheckRepository = inventoryCheckRepository;
        this.webClientBuilder = webClient;
        this.inventoryRepository = inventoryRepository;
    }


    @Transactional
    public void checkInventory(){

        logger.info("Entering checkInventory");
        //validate last check
        Optional<InventoryCheck> check = inventoryCheckRepository.findFirstByOrderByLastCheckDesc();

        logger.info("last check : "+check);

        if(check.isPresent() && differenceBetweenDate(check.get().getLastCheck(), LocalDateTime.now()) <= inMinuteUpdate) {
            logger.info("Returning  different : "+ differenceBetweenDate(check.get().getLastCheck(), LocalDateTime.now()));
            return;
        }

        // call all product

        ProductDTO[] responseList = webClientBuilder.build().get()
                .uri("http://product-service/v0.1/api/product")
                .retrieve().bodyToMono(ProductDTO[].class)
                .block();

        for (ProductDTO prod : responseList) {

            if(!inventoryRepository.findByProductCode(prod.productCode()).isPresent()){
                Inventory inventory =  new Inventory();
                inventory.setProductCode(prod.productCode());
                inventory.setQuantity(InventoryUtil.getRandomNumberInRange(1, 900));
                inventoryRepository.save(inventory);
            }
        }

        InventoryCheck checkUpdate = new InventoryCheck();
        checkUpdate.setLastCheck(LocalDateTime.now());
        checkUpdate.setProductCount(responseList.length);
        inventoryCheckRepository.save(checkUpdate);
        logger.info("Exiting checkInventory");
    }

    private long differenceBetweenDate(LocalDateTime dataOne , LocalDateTime dateTwo){
        long diff = ChronoUnit.MINUTES.between(dataOne, dateTwo);
        return diff;
    }


    public void checkUnavailableInventory() {
        logger.info("Entering checkUnavailableInventory");

        List<Inventory> inventoryList = inventoryRepository.findInventoryUnavailable();

        for (Inventory inventory : inventoryList) {
            inventoryRepository.delete(inventory);
        }
        logger.info("Exiting checkUnavailableInventory");
    }

    public Inventory checkAvailableInventory(String productCode, Integer quantity) {

        if(inventoryRepository.findByProductCode(productCode).isPresent()){
            Inventory inventory = inventoryRepository.findByProductCode(productCode).get();
            inventory.setQuantity(quantity);
            return inventoryRepository.save(inventory);
        }
        return null;
    }
}
