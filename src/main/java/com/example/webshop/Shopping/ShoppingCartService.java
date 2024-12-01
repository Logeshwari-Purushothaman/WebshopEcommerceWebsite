package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ProductService productService; // To fetch products by ID
    private final ShoppingCart shoppingCart; // The cart instance

    public ShoppingCartService(ProductService productService) {
        this.productService = productService;
        this.shoppingCart = new ShoppingCart(); // Initialize a new cart
    }

    // Add product to the cart by ID
    public void addProductToCart(Long productId) {
        Optional<ProductModel> productOpt = Optional.ofNullable(productService.getProductById(productId));

        if (productOpt.isPresent()) {
            ProductModel product = productOpt.get();
            // If the product is already in the cart, increase its quantity
            shoppingCart.products.merge(product, 1, Integer::sum);
        }
    }

    // Get the current shopping cart
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    // Calculate the total price of the shopping cart
    public double getTotalPrice() {
        return shoppingCart.getTotalPrice();
    }
}
