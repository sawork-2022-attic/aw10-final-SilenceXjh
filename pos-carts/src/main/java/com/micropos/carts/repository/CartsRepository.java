package com.micropos.carts.repository;

import java.util.ArrayList;
import java.util.List;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CartsRepository {
    
    private List<Cart> carts = new ArrayList<>();
    private int index = 0;

    public Flux<Cart> allCarts() {
        if(carts.isEmpty()) {
            return Flux.empty();
        }
        return Flux.fromIterable(carts);
    }

    public Mono<Cart> createCart() {
        Cart cart = new Cart(index);
        index++;
        carts.add(cart);
        return Mono.just(cart);
    }

    public Mono<Cart> findCart(int cartId) {
        for(Cart c : carts) {
            if(c.getCartId() == cartId) {
                return Mono.just(c);
            }
        }
        return Mono.empty();
    }

}