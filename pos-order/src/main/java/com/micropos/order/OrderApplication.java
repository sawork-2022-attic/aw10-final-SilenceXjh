package com.micropos.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {
    
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "order-server");

        SpringApplication.run(OrderApplication.class, args);
    }
}