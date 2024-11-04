package com.example.webshop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products") // Base path for all product-related endpoints
public class ProductController {

    // Static list to simulate a database
    private static final List<ProductModel> products = new ArrayList<>();

    static {
    	// Adding some sample products with the new attribute
        products.add(new ProductModel(1L, "T-Shirt", 19.99, "M", "Red", "Clothing"));
        products.add(new ProductModel(2L, "Jeans", 39.99, "L", "Blue", "Clothing"));
        products.add(new ProductModel(3L, "Sneakers", 59.99, "10", "Black", "Footwear"));
        products.add(new ProductModel(4L, "Hat", 15.99, "One Size", "Green", "Accessories"));
        products.add(new ProductModel(5L, "Jacket", 89.99, "L", "Gray", "Clothing"));
        products.add(new ProductModel(6L, "T-Shirt", 21.99, "L", "Black", "Clothing"));
    }

 // Endpoint for filtered view by color
    @GetMapping("/filter/color/{color}")
    public ResponseEntity<List<ProductModel>> getProductsByColor(@PathVariable String color) {
        List<ProductModel> filteredProducts = products.stream()
                .filter(product -> product.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredProducts);
    }

    // Endpoint for filtered view by category
    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> filteredProducts = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredProducts);
    }

    // Example endpoint for filtering by name (e.g., only T-Shirts)
    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<ProductModel>> getProductsByName(@PathVariable String name) {
        List<ProductModel> filteredProducts = products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredProducts);
    }
}
