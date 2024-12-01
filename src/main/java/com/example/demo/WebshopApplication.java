package com.example.webshop; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation indicates that this is a Spring Boot application
@SpringBootApplication(scanBasePackages = {"com.example.webshop", "com.example.usermanagement"})
public class WebshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }
}


