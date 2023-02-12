package com.microservice.order.cloud.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_items")
public class OrderLineItems implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productCode;

    private BigDecimal price;

    private Integer discount;

    private Integer quantity;

    @JoinColumn(name="order_id")
    private Long orderId;

    public OrderLineItems(Long id, String productCode, BigDecimal price, Integer discount, Integer quantity, Long orderId) {
        this.id = id;
        this.productCode = productCode;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public OrderLineItems(Long id, String productCode, BigDecimal price, Integer quantity, Long order) {
        this.id = id;
        this.productCode = productCode;
        this.price = price;
        this.quantity = quantity;
        this.orderId = order;
    }

    public OrderLineItems() {
    }

    public Long getOrder() {
        return orderId;
    }

    public void setOrder(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderLineItems{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", orderId=" + orderId +
                '}';
    }
}