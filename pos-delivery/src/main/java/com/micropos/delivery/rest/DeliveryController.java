package com.micropos.delivery.rest;

import java.util.List;

import com.micropos.api.DeliveriesApi;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.service.DeliveryService;
import com.micropos.dto.DeliveryDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class DeliveryController implements DeliveriesApi {
    
    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public Mono<ResponseEntity<Flux<DeliveryDto>>> allDeliveries(ServerWebExchange exchange) {
        Flux<DeliveryDto> dtos = deliveryService.allDeliveries().flatMap(d -> Mono.just(deliveryMapper.toDeliveryDto(d)));
        return Mono.just(new ResponseEntity<>(dtos, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<DeliveryDto>> showDeliveryById(Integer deliveryId, ServerWebExchange exchange) {
        Mono<Delivery> delivery = deliveryService.getDelivery(deliveryId);
        return delivery.flatMap(d -> Mono.just(new ResponseEntity<>(deliveryMapper.toDeliveryDto(d), HttpStatus.OK)))
                        .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}