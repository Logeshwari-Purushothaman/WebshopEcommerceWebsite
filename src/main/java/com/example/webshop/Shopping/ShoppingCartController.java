package com.example.webshop.Shopping;

import com.example.webshop.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService; // ShoppingCart service
    private final ProductService productService; // ProductService to get product by ID

    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    // View the shopping cart content
    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", shoppingCartService.getShoppingCart());
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice());
        return "cart";  // Return the cart.html template
    }

    // Add a product to the cart by product ID
    @GetMapping("/cart-add/{id}")
    public String addProductToCart(@PathVariable Long id) {
        shoppingCartService.addProductToCart(id);
        return "redirect:/cart";  // Redirect to the cart page after adding the product
    }
}
