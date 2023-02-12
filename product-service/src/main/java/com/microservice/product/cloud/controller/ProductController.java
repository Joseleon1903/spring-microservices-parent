package com.microservice.product.cloud.controller;

import com.microservice.product.cloud.mapper.ProductMapper;
import com.microservice.product.cloud.model.Product;
import com.microservice.product.cloud.record.ProductCreatedRequest;
import com.microservice.product.cloud.record.ProductUpdateRequest;
import com.microservice.product.cloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/v0.1/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreatedRequest product){
        Product prod = productService.save(ProductMapper.get(product));
        return new ResponseEntity(prod, HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@RequestBody ProductUpdateRequest product){
        Product prod = productService.update(ProductMapper.get(product));
        return new ResponseEntity(prod, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long productId){
        Product prod = productService.findProductById(productId).get();
        return new ResponseEntity(prod, HttpStatus.OK);
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseEntity<Product> findProductByCode(@RequestParam("productCode") String productCode){
        Product prod = productService.findProductByProductCode(productCode).get();
        return new ResponseEntity(prod, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Product>> findAllProducts(){
        return new ResponseEntity(ProductMapper.getOutputList(productService.findAllProducts()), HttpStatus.OK);
    }
}
