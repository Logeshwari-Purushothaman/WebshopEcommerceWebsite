package com.example.webshop.Shopping;

import com.example.webshop.Shopping.AddToCartFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final AddToCartFacade addToCartFacade;

    public ShoppingCartController(AddToCartFacade addToCartFacade) {
        this.addToCartFacade = addToCartFacade;
    }

    // View cart page
    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", addToCartFacade.getShoppingCart());  // Add the cart to the model
        model.addAttribute("totalPrice", addToCartFacade.getTotalPrice());  // Add the total price to the model
        return "cart"; // Return the cart view
    }

    // Add product to cart
    @GetMapping("/cart-add/{id}")
    public String addProductToCart(@PathVariable Long id, Model model) {
        try {
            addToCartFacade.addToCart(id);  // Add the product to the cart
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());  // Add error message if stock is insufficient
        }
        return "redirect:/shopping/cart";  // Redirect to the cart page
    }
}
