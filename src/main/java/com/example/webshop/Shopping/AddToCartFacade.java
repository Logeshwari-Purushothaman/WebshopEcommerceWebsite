package com.example.webshop.Shopping;

import com.example.webshop.*;
import com.example.webshop.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


/**
 * Facade service that handles operations related to adding products to the shopping cart.
 * This service interacts with the product, shopping cart, and inventory services to manage
 * product availability and cart updates.
 */
@Service
public class AddToCartFacade {

    private final InventoryService inventoryService;
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    /**
     * Constructor that injects dependencies for product, shopping cart, and inventory services.
     *
     * @param inventoryService The service responsible for managing inventory (stock levels).
     * @param shoppingCartService The service responsible for handling shopping cart operations.
     * @param productService The service responsible for managing product information.
     */
    @Autowired
    public AddToCartFacade(InventoryService inventoryService, ShoppingCartService shoppingCartService, ProductService productService) {
        this.inventoryService = inventoryService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    /**
     * Adds a product to the shopping cart if sufficient stock is available. If the product is
     * out of stock, an exception is thrown.
     *
     * @param productId The ID of the product to be added to the cart.
     * @return The updated shopping cart after the product is added.
     * @throws RuntimeException if the product is not found or there is insufficient stock.
     */
    public ShoppingCart addToCart(Long productId) {
        ProductModel product = productService.getProductById(productId);

        // Check if the product exists and has stock available
        if (product == null || product.getStock() <= 0) {
            throw new RuntimeException("Not enough stock for product: " + productId);
        }
        else
        {
            shoppingCartService.addProductToCart(productId); // Add product to the cart
        }
        
        // Return the updated cart
        return shoppingCartService.getShoppingCart();
    }
    
    /**
     * Retrieves the current shopping cart for the user.
     *
     * @return The current shopping cart.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    /**
     * Calculates and returns the total price of all products in the shopping cart.
     *
     * @return The total price of the items in the cart.
     */
    public BigDecimal getTotalPrice() {
        return shoppingCartService.getTotalPrice();
    }
}
