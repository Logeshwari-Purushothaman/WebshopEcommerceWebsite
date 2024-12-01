package com.example.webshop;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private List<ProductModel> products;
    

    // Constructor to initialize the product list
    public ProductService() {
        // Sample product list
        this.products = new ArrayList<>(List.of(
            new ProductModel(1L, "T-Shirt", 19.99, "M", "Red", "Clothing"),
            new ProductModel(2L, "Jeans", 39.99, "L", "Blue", "Clothing"),
            new ProductModel(3L, "Sneakers", 59.99, "10", "Black", "Footwear"),
            new ProductModel(4L, "Hat", 15.99, "One Size", "Green", "Accessories"),
            new ProductModel(5L, "Jacket", 89.99, "L", "Gray", "Clothing"),
            new ProductModel(6L, "T-Shirt", 21.99, "L", "Black", "Clothing"),
            // Add more hardcoded products for the catalog
            new ProductModel(7L, "Socks", 9.99, "M", "White", "Clothing"),
            new ProductModel(8L, "Scarf", 19.99, "One Size", "Red", "Accessories")
        ));
    }

    // Get all products
    public List<ProductModel> getAllProducts() {
        return products;
    }

    // Get a product by ID
    public ProductModel getProductById(Long id) {
        return products.stream()
                       .filter(product -> product.getId().equals(id))
                       .findFirst()
                       .orElse(null); // Return null if product not found
    }

    // Method to delete a product by ID
    public List<ProductModel> deleteProductById(Long id) {
        ProductModel productToRemove = products.stream()
                                               .filter(product -> product.getId().equals(id))
                                               .findFirst()
                                               .orElse(null);

        if (productToRemove != null) {
            products.remove(productToRemove);
            return products; // Return the updated list
        }
        return null; // Product not found
    }

    // Update a product
    public ProductModel updateProduct(ProductModel updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(updatedProduct.getId())) {
                products.set(i, updatedProduct); // Update the product
                return updatedProduct; // Return the updated product
            }
        }
        return null; // Product with the given ID not found
    }

    // Add a new product
    public boolean addProduct(ProductModel product) {
        // Check for duplicates by product name (case-insensitive)
        if (products.stream().anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()))) {
            return false; // Product already exists
        }

        // Assign a unique ID if not provided
        if (product.getId() == null) {
            product.setId((long) getAllProducts().size() + 1);
        }

        products.add(product);
        return true;
    }

    // Filter products by color
    public List<ProductModel> getProductsByColor(String color) {
        return products.stream()
                       .filter(product -> product.getColor().equalsIgnoreCase(color))
                       .collect(Collectors.toList());
    }

    // Filter products by category
    public List<ProductModel> getProductsByCategory(String category) {
        return products.stream()
                       .filter(product -> product.getCategory().equalsIgnoreCase(category))
                       .collect(Collectors.toList());
    }

    // Filter products by name
    public List<ProductModel> getProductsByName(String name) {
        return products.stream()
                       .filter(product -> product.getName().equalsIgnoreCase(name))
                       .collect(Collectors.toList());
    }
}
