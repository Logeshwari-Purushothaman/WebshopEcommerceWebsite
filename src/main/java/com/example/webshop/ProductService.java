package com.example.webshop;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<ProductModel> products;

    // Constructor to initialize the product list
    public ProductService() {
        // Sample product list
        this.products = List.of(
            new ProductModel(1L, "T-Shirt", 19.99, "M", "Red", "Clothing"),
            new ProductModel(2L, "Jeans", 39.99, "L", "Blue", "Clothing"),
            new ProductModel(3L, "Sneakers", 59.99, "10", "Black", "Footwear"),
            new ProductModel(4L, "Hat", 15.99, "One Size", "Green", "Accessories"),
            new ProductModel(5L, "Jacket", 89.99, "L", "Gray", "Clothing"),
            new ProductModel(6L, "T-Shirt", 21.99, "L", "Black", "Clothing")
        );
    }

    // Method to filter products by color
    public List<ProductModel> getProductsByColor(String color) {
        return products.stream()
                .filter(product -> product.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    // Method to filter products by category
    public List<ProductModel> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Method to filter products by name
    public List<ProductModel> getProductsByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
}
