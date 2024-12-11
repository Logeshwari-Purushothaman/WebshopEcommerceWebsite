package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart in the system.
 * The shopping cart holds products and their quantities, allowing users to manage their purchases.
 */
public class ShoppingCart {

    /**
     * A map that associates products with their quantities in the cart.
     * The key is the product and the value is the quantity of that product in the cart.
     */
    private final Map<ProductModel, Integer> products = new HashMap<>();

    /**
     * Gets the map of products currently in the shopping cart along with their quantities.
     * 
     * @return A map where the key is a product and the value is the quantity of that product.
     */
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
