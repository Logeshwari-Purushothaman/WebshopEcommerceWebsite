package com.example.webshop; // Adjust the package name accordingly

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    // Static list of example products
    private static final List<ProductModel> products = new ArrayList<>();

    static {
        products.add(new ProductModel(1L, "T-Shirt", 19.99, "M", "Red"));
        products.add(new ProductModel(2L, "Jeans", 39.99, "L", "Blue"));
        products.add(new ProductModel(3L, "Sneakers", 49.99, "42", "Black"));
        products.add(new ProductModel(4L, "Hoodie", 29.99, "S", "Green"));
        products.add(new ProductModel(5L, "Cap", 15.99, "One Size", "White"));
    }

    // Endpoint to get the full list of products
    @GetMapping("/products")
    public List<ProductModel> getProducts() {
        return products;
    }
}
