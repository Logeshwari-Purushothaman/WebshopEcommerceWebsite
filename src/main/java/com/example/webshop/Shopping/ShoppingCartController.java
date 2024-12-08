package com.example.webshop.Shopping;

import com.example.webshop.Shopping.AddToCartFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final AddToCartFacade addToCartFacade;


    public ShoppingCartController(AddToCartFacade addToCartFacade) {
        this.addToCartFacade = addToCartFacade;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", addToCartFacade.getShoppingCart());
        model.addAttribute("totalPrice", addToCartFacade.getTotalPrice());
        return "cart"; // Cart template view
    }

    @GetMapping("/cart-add/{id}")
    public String addProductToCart(@PathVariable Long id, Model model) {
        try {
            addToCartFacade.addToCart(id);  // Add the product to the cart
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());  // Display error message if stock is insufficient
            return "redirect:/shopping/cart";  // Redirect to the cart page with error message
        }
        return "redirect:/shopping/cart";  // Redirect to the cart page after adding the product
    }
}

