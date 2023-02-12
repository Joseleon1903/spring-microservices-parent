package com.microservice.order.cloud.controller;

import com.microservice.order.cloud.model.Order;
import com.microservice.order.cloud.record.CreatedOrderRequest;
import com.microservice.order.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/v0.1/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Order> createOrder(@RequestBody CreatedOrderRequest createdOrderRequest){
        Order orderOut = orderService.addOrder(createdOrderRequest);
        return new ResponseEntity(orderOut, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orderListOut = orderService.findAllOrders();
        return new ResponseEntity(orderListOut, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id){
        Order orderOut = orderService.findOrderById(id);
        return new ResponseEntity(orderOut, HttpStatus.OK);
    }



}