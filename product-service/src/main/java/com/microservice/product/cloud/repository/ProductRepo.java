package com.microservice.product.cloud.repository;

import com.microservice.product.cloud.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepo extends CrudRepository<Product, Long> {
    Optional<Product> findProductByProductCode(String productCode);
}
