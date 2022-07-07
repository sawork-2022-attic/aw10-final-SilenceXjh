package com.micropos.carts.rest;

import java.util.ArrayList;
import java.util.List;

import com.micropos.api.CartsApi;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;

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
public class CartController implements CartsApi {

    private CartMapper cartMapper;
    private CartService cartService;

    @Autowired
    CartController(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }
    
    @Override
    public Mono<ResponseEntity<CartDto>> addItemToCart(Integer cartId, Mono<CartItemDto> cartItemDto, ServerWebExchange exchange) {
        Mono<Cart> cart = cartItemDto.flatMap(dto -> cartService.addItemToCart(cartId, cartMapper.toItem(dto)));
        return cart.flatMap(c -> Mono.just(new ResponseEntity<>(cartMapper.toCartDto(c), HttpStatus.OK)))
                    .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ResponseEntity<CartDto>> createCart(ServerWebExchange exchange) {
        Mono<Cart> cart = cartService.createCart();
        return cart.flatMap(c -> Mono.just(new ResponseEntity<>(cartMapper.toCartDto(c), HttpStatus.OK)))
                    .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ResponseEntity<CartDto>> getCartById(Integer cartId, ServerWebExchange exchange) {
        Mono<Cart> cart = cartService.getCart(cartId);
        return cart.flatMap(c -> Mono.just(new ResponseEntity<>(cartMapper.toCartDto(c), HttpStatus.OK)))
                    .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ResponseEntity<Flux<CartDto>>> listCarts(ServerWebExchange exchange) {
        Flux<CartDto> carts = cartService.carts().flatMap(c -> Mono.just(cartMapper.toCartDto(c)));
        return Mono.just(new ResponseEntity<>(carts, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Double>> showCartTotal(Integer cartId, ServerWebExchange exchange) {
        Mono<Double> total = cartService.getTotal(cartId);
        return total.flatMap(t -> Mono.just(new ResponseEntity<>(t, HttpStatus.OK)))
                    .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ResponseEntity<Double>> checkout(Integer cartId, ServerWebExchange exchange) {
        Mono<Double> total = cartService.checkout(cartId);
        return total.flatMap(t -> Mono.just(new ResponseEntity<>(t, HttpStatus.OK)))
                    .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

}