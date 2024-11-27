package com.ua.yushchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ua.yushchenko.client")
public class ShowScoutServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowScoutServiceApplication.class, args);
    }
}