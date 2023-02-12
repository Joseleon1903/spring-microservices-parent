package com.microservice.product.cloud.record;

import java.math.BigDecimal;

public record ProductCreatedRequest(String productTag, String description, BigDecimal price) {
}
