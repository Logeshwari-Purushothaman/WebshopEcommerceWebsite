package com.example.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example.webshop", "com.example.usermanagement" })
public class WebshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}
}
