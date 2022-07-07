package com.micropos.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class ProductsApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "product-server");

        SpringApplication.run(ProductsApplication.class, args);
    }
}