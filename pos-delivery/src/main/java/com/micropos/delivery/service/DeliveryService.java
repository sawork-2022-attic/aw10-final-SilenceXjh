package com.micropos.delivery.service;

import java.util.List;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Order;
import com.micropos.delivery.repository.DeliveryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryService {

    private int queryNum = 0;
    
    @Autowired
    private DeliveryRepository deliveryRepository;

    public Mono<Delivery> createDelivery(Order order) {
        return deliveryRepository.createDelivery(order);
    }

    public Flux<Delivery> allDeliveries() {
        return deliveryRepository.allDeliveries();
    }

    public Mono<Delivery> getDelivery(int id) {
        queryNum++;
        if(queryNum % 2 == 0) {
            deliveryRepository.switchStatus(id);
        }
        return deliveryRepository.getDelivery(id);
    }

}