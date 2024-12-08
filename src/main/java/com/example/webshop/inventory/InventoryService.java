package com.example.webshop.inventory;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {

    // A map to store productId and stock count
    private final Map<Long, Integer> productStock = new HashMap<>();
    
    // Get stock for a specific productId
    public Integer getStock(Long productId) {
        return productStock.getOrDefault(productId, 0); // Return 0 if the productId doesn't exist
    }

    // Reduce stock by a specified quantity
    public boolean reduceStock(Long productId, int quantity) {
        int currentStock = productStock.getOrDefault(productId, 0);
        if (currentStock >= quantity) {
            productStock.put(productId, currentStock - quantity); // Reduce stock by the quantity
            return true;  // Successfully reduced the stock
        } else {
            return false;  // Not enough stock
        }
    }

    // Add stock for a specific productId
    public void addStock(Long productId, int quantity) {
        productStock.put(productId, productStock.getOrDefault(productId, 0) + quantity);
    }

    // Initialize stock for a specific productId (if not initialized)
    public void initializeStock(Long productId, int initialStock) {
        productStock.putIfAbsent(productId, initialStock);
    }
    
    // Check if a product is out of stock
    public boolean isOutOfStock(Long productId) {
        return getStock(productId) <= 0;
    }
}
