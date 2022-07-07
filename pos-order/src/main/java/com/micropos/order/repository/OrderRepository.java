package com.micropos.order.repository;

import java.util.ArrayList;
import java.util.List;

import com.micropos.order.model.Item;
import com.micropos.order.model.Order;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class OrderRepository {
    
    private int num = 0;
    private List<Order> orders = new ArrayList<>();

    public Mono<Order> createOrder(List<Item> items) {
        Order order = new Order(num, items);
        num++;
        orders.add(order);
        return Mono.just(order);
    }
}