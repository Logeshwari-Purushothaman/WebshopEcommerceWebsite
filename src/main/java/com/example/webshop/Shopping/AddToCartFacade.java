package com.example.webshop.Shopping;

import com.example.webshop.*;
import com.example.webshop.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddToCartFacade {

    private final InventoryService inventoryService;
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @Autowired
    public AddToCartFacade(InventoryService inventoryService, ShoppingCartService shoppingCartService, ProductService productService) {
        this.inventoryService = inventoryService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    // Method to add product to the shopping cart with stock validation
    public ShoppingCart addToCart(Long productId) {
        ProductModel product = productService.getProductById(productId);

        if (product == null || product.getStock() <= 0) {
            throw new RuntimeException("Not enough stock for product: " + productId);
        }
        else
        {
        	 shoppingCartService.addProductToCart(productId); // Add product to the cart
        }
        return shoppingCartService.getShoppingCart(); // Return the updated cart
    }
    
    // Method to get the current shopping cart
    public ShoppingCart getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    // Method to get the total price of the cart
    public double getTotalPrice() {
        return shoppingCartService.getTotalPrice();
    }
}
