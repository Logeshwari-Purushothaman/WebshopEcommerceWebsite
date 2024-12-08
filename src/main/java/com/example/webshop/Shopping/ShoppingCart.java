package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<ProductModel, Integer> products = new HashMap<>();

    public Map<ProductModel, Integer> getProducts() {
        return products;
    }

    /*
    // Method to calculate the total price of all products in the cart
    public double getTotalPrice() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
    */
}
