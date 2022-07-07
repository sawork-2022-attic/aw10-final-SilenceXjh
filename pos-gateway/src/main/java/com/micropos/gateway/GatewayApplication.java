package com.micropos.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {
    
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "gateway-server");

        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p
                .path("/api/products")
                .filters(f -> f.circuitBreaker(config -> config.setName("products-error")))
                .uri("http://localhost:2222"))
            .route(p -> p
                .path("/api/products/{productId}")
                .filters(f -> f.circuitBreaker(config -> config.setName("product-error")))
                .uri("http://localhost:2222"))
            .route(p -> p
                .path("/api/carts")
                .filters(f -> f.circuitBreaker(config -> config.setName("carts-error")))
                .uri("http://localhost:3333"))
            .route(p -> p
                .path("/api/carts/{cartId}")
                .filters(f -> f.circuitBreaker(config -> config.setName("cart-error")))
                .uri("http://localhost:3333"))
            .route(p -> p
                .path("/api/carts/{cartId}/total")
                .filters(f -> f.circuitBreaker(config -> config.setName("cartTotal-error")))
                .uri("http://localhost:3333"))
            .route(p -> p
                .path("/api/carts/{cartId}/checkout")
                .filters(f -> f.circuitBreaker(config -> config.setName("cartCheckout-error")))
                .uri("http://localhost:3333"))
            .route(p -> p
                .path("/api/deliveries")
                .filters(f -> f.circuitBreaker(config -> config.setName("deliveries-error")))
                .uri("http://localhost:5555"))
            .route(p -> p
                .path("/api/deliveries/{deliveryId}")
                .filters(f -> f.circuitBreaker(config -> config.setName("delivery-error")))
                .uri("http://localhost:5555"))
            .build();
    }
}