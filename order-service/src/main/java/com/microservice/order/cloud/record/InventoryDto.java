package com.microservice.order.cloud.record;

public record InventoryDto(
        Long id,
        String productCode,
        Integer quantity) {
}
