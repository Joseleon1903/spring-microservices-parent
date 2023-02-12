package com.microservice.order.cloud.service;

import com.microservice.order.cloud.mapper.OrderMapper;
import com.microservice.order.cloud.model.Order;
import com.microservice.order.cloud.model.OrderLineItems;
import com.microservice.order.cloud.record.CreatedOrderRequest;
import com.microservice.order.cloud.record.InStockResponse;
import com.microservice.order.cloud.record.InventoryDto;
import com.microservice.order.cloud.record.ProductDTO;
import com.microservice.order.cloud.repository.OrderLineItemsRepo;
import com.microservice.order.cloud.repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepo orderRepo;

    private final WebClient.Builder webClientBuilder;

    private final OrderLineItemsRepo orderLineItemsRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, OrderLineItemsRepo orderLineItemsRepo, WebClient.Builder webClient) {
        this.orderRepo = orderRepo;
        this.orderLineItemsRepo = orderLineItemsRepo;
        this.webClientBuilder = webClient;
    }

    public Order addOrder(CreatedOrderRequest createdOrderRequest) {

        logger.info("entering addOrder");

        logger.info("call webClient");

        // validate if product is in stock
        InStockResponse response =webClientBuilder.build().get()
                                            .uri("http://inventory-service/v0.1/api/inventory/"+createdOrderRequest.productCode())
                                            .retrieve().bodyToFlux(InStockResponse.class)
                                            .blockFirst();
        logger.info("response webClient");
        logger.info("response :"+response);

        if(!response.inStock()){
            throw new RuntimeException();
        }
        logger.info("product is available : "+createdOrderRequest.productCode());

        //find product
        ProductDTO productReponse =webClientBuilder.build().get()
                .uri("http://product-service/v0.1/api/product/find",
                        uriBuilder -> uriBuilder.queryParam("productCode", createdOrderRequest.productCode()).build())
                .retrieve().bodyToMono(ProductDTO.class)
                .block();

        BigDecimal price = productReponse.price().multiply(BigDecimal.valueOf(createdOrderRequest.quantity()));
        BigDecimal discount = BigDecimal.ZERO;
        if(createdOrderRequest.discount() > 0){
            discount = (BigDecimal.valueOf(createdOrderRequest.discount()).divide(BigDecimal.valueOf(100))).multiply(price);
        }
        logger.info("apply discount : "+discount);
        price =price.subtract(discount);

        OrderLineItems orderItemsEntity = OrderMapper.get(createdOrderRequest);
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order = orderRepo.save(order);

        orderItemsEntity.setOrder(order.getOrderId());
        orderItemsEntity.setPrice(price);
        orderLineItemsRepo.save(orderItemsEntity);
        Order orderOut =orderRepo.findById(order.getOrderId()).get();

        //actualizando inventario
        InventoryDto inventory =webClientBuilder.build().get().uri("http://inventory-service/v0.1/api/inventory/find/"+createdOrderRequest.productCode())
                .retrieve()
                .bodyToMono(InventoryDto.class).block();

        Integer quantityUpdate = inventory.quantity() - createdOrderRequest.quantity();
        if(quantityUpdate < 0){
            quantityUpdate = 0;
        }
        Integer finalQuantityUpdate = quantityUpdate;
        InventoryDto inventoryUpdated = webClientBuilder.build().put().uri("http://inventory-service/v0.1/api/inventory",
                uriBuilder -> uriBuilder.queryParam("productCode",createdOrderRequest.productCode())
                        .queryParam("quantity", finalQuantityUpdate).build())
                .retrieve().bodyToMono(InventoryDto.class).block();
        logger.info("inventory update : "+inventoryUpdated);
        return orderOut;
    }


    public List<Order> findAllOrders() {
        return orderRepo.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepo.findById(id).get();
    }
}
