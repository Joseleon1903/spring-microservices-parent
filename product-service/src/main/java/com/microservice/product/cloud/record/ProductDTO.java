package com.microservice.product.cloud.record;
import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String productCode,
        String productTag,
        String description,
        BigDecimal price) {
}
