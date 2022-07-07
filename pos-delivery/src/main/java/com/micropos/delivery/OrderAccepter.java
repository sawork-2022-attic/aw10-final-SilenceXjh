package com.micropos.delivery;

import java.util.function.Consumer;

import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.model.Order;
import com.micropos.delivery.service.DeliveryService;
import com.micropos.dto.OrderDto;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Mono;

public class OrderAccepter implements Consumer<OrderDto> {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public void accept(OrderDto orderDto) {
        deliveryService.createDelivery(deliveryMapper.toOrder(orderDto));
        System.out.println("Create a delivery");
    }
}