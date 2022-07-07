package com.micropos.carts.service;

import java.util.List;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {

    public Mono<Cart> getCart(int cartId);

    public Mono<Cart> createCart();

    public Flux<Cart> carts();
    
    public Mono<Cart> addItemToCart(int cartId, Item item);

    public Mono<Double> getTotal(int cartId);

    public Mono<Double> checkout(int cartId);

}