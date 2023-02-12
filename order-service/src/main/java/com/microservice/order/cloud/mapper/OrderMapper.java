package com.microservice.order.cloud.mapper;

import com.microservice.order.cloud.model.OrderLineItems;
import com.microservice.order.cloud.model.Order;
import com.microservice.order.cloud.record.CreatedOrderRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderMapper {

    public static OrderLineItems get(CreatedOrderRequest request){
        OrderLineItems items =  new OrderLineItems();
        items.setProductCode(request.productCode());
        items.setDiscount(request.discount());
        items.setQuantity(request.quantity());
        return items;
    }


}
