package com.microservice.order.cloud.record;

public record CreatedOrderRequest(String productCode, Integer discount, Integer quantity) {
}
