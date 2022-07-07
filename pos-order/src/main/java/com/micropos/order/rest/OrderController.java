package com.micropos.order.rest;

import com.micropos.api.OrdersApi;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class OrderController implements OrdersApi {
    
    private OrderMapper orderMapper;

    private OrderService orderService;

    @Autowired
    private StreamBridge streamBridge;
    
    @Autowired
    public OrderController(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @Override
    public Mono<ResponseEntity<OrderDto>> createOrder(Flux<CartItemDto> cartItemDto, ServerWebExchange exchange) {
        /*Order order = orderService.createOrder(orderMapper.toItems(cartItemDto));
        streamBridge.send("orderOutput", orderMapper.toOrderDto(order));
        return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);*/
        Mono<Order> order = cartItemDto.collectList().flatMap(l -> orderService.createOrder(orderMapper.toItems(l)));
        Mono<OrderDto> orderdto = order.flatMap(o -> { streamBridge.send("orderOutput", orderMapper.toOrderDto(o)); 
                                                        return Mono.just(orderMapper.toOrderDto(o)); });
        System.out.println("create and send order");
        return orderdto.flatMap(o -> Mono.just(new ResponseEntity<>(o, HttpStatus.OK)));
    }

}