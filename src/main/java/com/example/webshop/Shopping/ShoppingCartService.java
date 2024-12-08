package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import org.springframework.stereotype.Service;
import com.example.webshop.inventory.*;

import java.util.Optional;

@Service
public class ShoppingCartService
{

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ShoppingCart shoppingCart;
    

    public ShoppingCartService(ProductService productService,InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.shoppingCart = new ShoppingCart(); // Initialize the shopping cart
    }

    public void addProductToCart(Long productId) {
        ProductModel product = productService.getProductById(productId);
        if (product != null) {
            // First, check if there is enough stock
            int stockAvailable = inventoryService.getStock(productId);

            // If stock is greater than 0, reduce stock and add to cart
            if (stockAvailable > 0) {
                boolean stockReduced = inventoryService.reduceStock(productId, 1);
                if (stockReduced) {
                    shoppingCart.getProducts().merge(product, 1, Integer::sum);
                } else {
                    throw new RuntimeException("Not enough stock for product: " + productId);
                }
            } else {
                throw new RuntimeException("Not enough stock for product: " + productId);
            }
        } else {
            throw new RuntimeException("Product not found: " + productId);
        }
    }


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public double getTotalPrice() {
        return shoppingCart.getProducts()
                           .entrySet()
                           .stream()
                           .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                           .sum();
    }
}
