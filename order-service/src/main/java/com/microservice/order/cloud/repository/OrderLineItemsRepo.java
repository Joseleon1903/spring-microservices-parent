package com.microservice.order.cloud.repository;

import com.microservice.order.cloud.model.OrderLineItems;
import org.springframework.data.repository.CrudRepository;

public interface OrderLineItemsRepo extends CrudRepository<OrderLineItems, Long> {


}
