package com.example.webshop.Shopping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", shoppingCartService.getShoppingCart());
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice());
        return "cart"; // Cart template view
    }

    @GetMapping("/cart-add/{id}")
    public String addProductToCart(@PathVariable Long id) {
        shoppingCartService.addProductToCart(id); // Use the service to add product
        return "redirect:/shopping/cart"; // Redirect to the cart view
    }
}

