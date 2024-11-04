package com.example.webshop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService; // Use final for better design

    // Constructor-based Dependency Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint for filtered view by color
    @GetMapping("/filter/color/{color}")
    public ResponseEntity<List<ProductModel>> getProductsByColor(@PathVariable String color) {
        List<ProductModel> filteredProducts = productService.getProductsByColor(color);
        return ResponseEntity.ok(filteredProducts);
    }

    // Endpoint for filtered view by category
    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> filteredProducts = productService.getProductsByCategory(category);
        return ResponseEntity.ok(filteredProducts);
    }

    // Endpoint for filtered view by name
    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<ProductModel>> getProductsByName(@PathVariable String name) {
        List<ProductModel> filteredProducts = productService.getProductsByName(name);
        return ResponseEntity.ok(filteredProducts);
    }
}
