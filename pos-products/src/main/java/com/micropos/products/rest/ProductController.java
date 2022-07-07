package com.micropos.products.rest;

import java.util.ArrayList;
import java.util.List;

import com.micropos.api.ProductsApi;
import com.micropos.dto.ProductDto;
import com.micropos.products.mapper.ProductMapper;
import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;

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
public class ProductController implements ProductsApi {
    
    private final ProductMapper productMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductDto>>> listProducts(ServerWebExchange exchange) {
        
        Flux<ProductDto> products = productService.products().flatMap(p -> Mono.just(productMapper.toProductDto(p)));
        return Mono.just(new ResponseEntity<>(products, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<ProductDto>> showProductById(String productId, ServerWebExchange exchange) {
        
        return productService.getProduct(productId).flatMap(p -> Mono.just(new ResponseEntity<>(productMapper.toProductDto(p), HttpStatus.OK)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }
}