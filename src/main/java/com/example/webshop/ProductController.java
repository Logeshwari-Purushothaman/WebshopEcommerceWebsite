package com.example.webshop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Base mapping for all product-related endpoints
public class ProductController {

    private final ProductService productService; // Use final for better design

    // Constructor-based Dependency Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Controller is working!");
    }

    
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(
    		@RequestBody ProductModel product) {
        // Generate unique ID or handle ID elsewhere
        boolean isAdded = productService.addProduct(product);
        if (isAdded) {
            return ResponseEntity.ok("Product added successfully!");
        } else {
            return ResponseEntity.status(400).body("Product already exists!");
        }
    }
    
    // Endpoint for to get all products
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
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
