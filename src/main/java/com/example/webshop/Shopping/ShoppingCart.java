package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    public Map<ProductModel, Integer> products = new HashMap<>();  // Key: Product, Value: Quantity

    // Method to calculate the total price of the shopping cart
    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<ProductModel, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}
