package com.example.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point of the Webshop application.
 * This class contains the main method to launch the Spring Boot application.
 * It configures Spring Boot to scan for components in both the 'com.example.webshop' and 'com.example.usermanagement' packages.
 */
@SpringBootApplication(scanBasePackages = { "com.example.webshop", "com.example.usermanagement" })
public class WebshopApplication {

    /**
     * The main method that starts the Spring Boot application.
     * This method runs the application and initializes the Spring context.
     * It scans the specified base packages for components, configurations, and services.
     *
     * @param args The command-line arguments passed to the application (not used in this case).
     */
	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}
}
