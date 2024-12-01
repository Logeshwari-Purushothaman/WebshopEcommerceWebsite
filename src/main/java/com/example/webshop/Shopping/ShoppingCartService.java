package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService
{

    private final ProductService productService;
    private final ShoppingCart shoppingCart;

    public ShoppingCartService(ProductService productService) {
        this.productService = productService;
        this.shoppingCart = new ShoppingCart(); // Initialize the shopping cart
    }

    public void addProductToCart(Long productId) {
        ProductModel product = productService.getProductById(productId);
        if (product != null) {
            shoppingCart.getProducts().merge(product, 1, Integer::sum);
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
