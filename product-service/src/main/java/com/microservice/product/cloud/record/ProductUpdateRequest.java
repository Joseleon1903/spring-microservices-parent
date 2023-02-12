package com.microservice.product.cloud.record;

import java.math.BigDecimal;

public record ProductUpdateRequest(Long id, String productTag, String description, BigDecimal price) {
}
