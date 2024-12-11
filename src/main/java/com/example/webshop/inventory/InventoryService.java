package com.example.webshop.inventory;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class to manage inventory operations.
 * <p>
 * This service provides functionalities for managing product inventory, 
 * including adding, reducing, and querying stock levels for products.
 * </p>
 */
@Service
public class InventoryService {

    /**
     * A map to store product stock levels, where the key is the product ID 
     * and the value is the stock count.
     */
    private final Map<Long, Integer> productStock = new HashMap<>();

    /**
     * Retrieves the current stock level for a specific product.
     * 
     * @param productId the unique identifier of the product
     * @return the stock count for the product, or 0 if the product is not found
     */
    public Integer getStock(Long productId) {
        return productStock.getOrDefault(productId, 0);
    }

    /**
     * Reduces the stock level for a specific product by a given quantity.
     * 
     * @param productId the unique identifier of the product
     * @param quantity the quantity to reduce from the current stock
     * @return {@code true} if the stock was successfully reduced, {@code false} if insufficient stock
     */
    public boolean reduceStock(Long productId, int quantity) {
        int currentStock = productStock.getOrDefault(productId, 0);
        if (currentStock >= quantity) {
            productStock.put(productId, currentStock - quantity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a specified quantity to the stock level for a product.
     * 
     * @param productId the unique identifier of the product
     * @param quantity the quantity to add to the current stock
     */
    public void addStock(Long productId, int quantity) {
        productStock.put(productId, productStock.getOrDefault(productId, 0) + quantity);
    }

    /**
     * Initializes the stock level for a product if it hasn't been initialized already.
     * 
     * @param productId the unique identifier of the product
     * @param initialStock the initial stock level to set for the product
     */
    public void initializeStock(Long productId, int initialStock) {
        productStock.putIfAbsent(productId, initialStock);
    }

    /**
     * Checks if a product is out of stock.
     * 
     * @param productId the unique identifier of the product
     * @return {@code true} if the product is out of stock, {@code false} otherwise
     */
    public boolean isOutOfStock(Long productId) {
        return getStock(productId) <= 0;
    }

    /**
     * Removes a product's stock entry from the inventory.
     * 
     * @param productId the unique identifier of the product
     */
    public void removeStock(Long productId) {
        productStock.remove(productId);
    }
}
