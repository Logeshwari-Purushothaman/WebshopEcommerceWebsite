package com.example.demo; // Adjust the package name accordingly

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple REST controller that provides an endpoint to return a "Hello World" message.
 * <p>
 * This controller is mapped to handle HTTP requests and provides a single 
 * endpoint for demonstration purposes.
 * </p>
 */
@RestController
public class HelloController {

    /**
     * Handles GET requests to the "/url" endpoint.
     * <p>
     * This method returns a simple "Hello World" message as a response.
     * </p>
     *
     * @return a string containing "Hello World"
     */
    @GetMapping("/url")
    public String helloWorld() {
        return "Hello World";
    }
}
