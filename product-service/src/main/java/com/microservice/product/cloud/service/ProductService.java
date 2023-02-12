package com.microservice.product.cloud.service;

import com.microservice.product.cloud.model.Product;
import com.microservice.product.cloud.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product save(Product product){
        logger.info("Entering save product");
        return productRepo.save(product);
    }

    public Product update(Product product){
        logger.info("Entering update product");
        return productRepo.save(product);
    }

    public Optional<Product> findProductById(long productId){
        logger.info("Entering findProductById");
        return productRepo.findById(productId);
    }

    public List<Product> findAllProducts(){
        logger.info("Entering findAllProducts");
        return (List<Product>) productRepo.findAll();
    }

    public Optional<Product> findProductByProductCode(String productCode) {
        logger.info("Entering findProductByProductCode");
        return productRepo.findProductByProductCode(productCode);
    }
}
