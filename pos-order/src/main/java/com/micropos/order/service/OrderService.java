package com.micropos.order.service;

import java.util.List;

import com.micropos.order.model.Item;
import com.micropos.order.model.Order;
import com.micropos.order.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public Mono<Order> createOrder(List<Item> items) {
        System.out.println("Create an order");
        return orderRepository.createOrder(items);
    }
}