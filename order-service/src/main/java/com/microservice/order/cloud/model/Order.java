package com.microservice.order.cloud.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private String orderNumber;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL , fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderLineItems> orderLineItemsList;


    public Order(Long id, String orderNumber, Set<OrderLineItems> orderLineItemsList) {
        this.orderId = id;
        this.orderNumber = orderNumber;
        this.orderLineItemsList = orderLineItemsList;
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<OrderLineItems> getOrderLineItemsList() {
        if(orderLineItemsList == null){
           orderLineItemsList =  new HashSet<>();
        }
        return orderLineItemsList;
    }

    public void setOrderLineItemsList(Set<OrderLineItems> orderLineItemsList) {
        this.orderLineItemsList = orderLineItemsList;
    }

}
