package com.example.demo; // Adjust the package name accordingly

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/url")
    public String helloWorld() {
        return "Hello World";
    }
}
