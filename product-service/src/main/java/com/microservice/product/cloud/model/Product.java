package com.microservice.product.cloud.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String productCode;

    private String productTag;

    private String description;

    private BigDecimal price;

    public Product(Long id, String productCode, String productTag, String description, BigDecimal price) {
        this.id = id;
        this.productCode = productCode;
        this.productTag = productTag;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", product_tag='" + productTag + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
