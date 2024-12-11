package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 * <p>
 * This class initializes and runs the Spring application. It uses the 
 * {@code @SpringBootApplication} annotation to enable auto-configuration, 
 * component scanning, and configuration. The specified base packages 
 * include {@code com.example.webshop} and {@code com.example.usermanagement}, 
 * which are scanned for Spring components.
 * </p>
 */
@SpringBootApplication(scanBasePackages = { "com.example.webshop", "com.example.usermanagement" })
public class DemoApplication {

    /**
     * Main method to launch the Spring Boot application.
     * 
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
