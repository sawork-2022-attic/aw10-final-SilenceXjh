package com.micropos.carts.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.CartsRepository;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImp implements CartService {
    
    @Autowired
    private CartsRepository cartsRepository;

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CartMapper cartMapper;

    private WebClient webClient = WebClient.create();

    @Override
    @Cacheable(value = "cartCache", key = "#cartId")
    public Mono<Cart> getCart(int cartId) {
        return cartsRepository.findCart(cartId);
    }

    @Override 
    public Mono<Cart> createCart() {
        return cartsRepository.createCart();
    }

    @Override
    @Cacheable(value = "cartCache", key = "'carts'")
    public Flux<Cart> carts() {
        return cartsRepository.allCarts();
    }

    @Override
    public Mono<Cart> addItemToCart(int cartId, Item item) {
        Mono<Cart> cart = cartsRepository.findCart(cartId);
        return cart.flatMap(c -> { c.addItem(item); return Mono.just(c); });
    }

    @Override
    public Mono<Double> getTotal(int cartId) {
        Mono<Cart> cart = getCart(cartId);
        return cart.flatMap(c -> Mono.just(c.getTotal()))
                    .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Double> checkout(int cartId) {
        Mono<Cart> cart = getCart(cartId);
        //Flux<CartItemDto> dtos = cart.flatMapIterable(c -> cartMapper.toItemDtos(c.getItems()));
        Cart ca = cart.block();
        
        webClient.post()
                            .uri("http://localhost:4444/api/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Flux.fromIterable(cartMapper.toItemDtos(ca.getItems())), Item.class)
                            .retrieve()
                            .toEntity(OrderDto.class)
                            .subscribe();
        
        double total = ca.getTotal();
        ca.clear();
        return Mono.just(total);
    }

}