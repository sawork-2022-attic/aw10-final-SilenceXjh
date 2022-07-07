package com.micropos.delivery;

import java.util.function.Consumer;

import com.micropos.dto.OrderDto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class DeliveryApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "delivery-server");

        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Bean
    public Consumer<OrderDto> acceptOrder() {
        return new OrderAccepter();
    }
}