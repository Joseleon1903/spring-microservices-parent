package com.microservice.order.cloud.repository;

import com.microservice.order.cloud.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> findAll();
}
