package com.micropos.delivery.repository;

import java.util.ArrayList;
import java.util.List;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Order;
import com.micropos.delivery.model.Status;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class DeliveryRepository {
    
    private List<Delivery> deliveries = new ArrayList<>();

    private int num = 0;

    public Mono<Delivery> createDelivery(Order order) {
        Delivery delivery = new Delivery(num, order);
        num++;
        deliveries.add(delivery);
        return Mono.just(delivery);
    }
    
    public Flux<Delivery> allDeliveries() {
        return Flux.fromIterable(deliveries);
    }

    public Mono<Delivery> getDelivery(int id) {
        for(Delivery delivery : deliveries) {
            if(delivery.getId() == id) {
                return Mono.just(delivery);
            }
        }
        return Mono.empty();
    }

    public void switchStatus(int id) {
        for(Delivery delivery : deliveries) {
            if(delivery.getId() == id) {
                delivery.setStatus(Status.REACHED);
            }
        }
    }
}