package com.example.webshop.Shopping;

import com.example.webshop.PriceCalculationService; // Import the PriceCalculationService

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class that handles requests related to the shopping cart functionality.
 * This class is responsible for managing the cart view and adding products to the cart.
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final AddToCartFacade addToCartFacade;
    private final PriceCalculationService priceCalculationService; // Inject the price calculation service

    /**
     * Constructor-based dependency injection for the AddToCartFacade and PriceCalculationService.
     * 
     * @param addToCartFacade the AddToCartFacade to manage shopping cart operations.
     * @param priceCalculationService the service to round prices.
     */
    public ShoppingCartController(AddToCartFacade addToCartFacade, PriceCalculationService priceCalculationService) {
        this.addToCartFacade = addToCartFacade;
        this.priceCalculationService = priceCalculationService;
    }

    /**
     * Handles the request to view the cart page.
     * Adds the current shopping cart and total price to the model, which will be rendered on the cart page.
     * 
     * @param model the model to add attributes to, which will be passed to the view.
     * @return the name of the view to render, which in this case is "cart".
     */
    @GetMapping("/cart")
    public String getCart(Model model) {
        // Get the total price from the shopping cart service
        BigDecimal totalPrice = addToCartFacade.getTotalPrice();

        // Round the total price using the PriceCalculationService
        BigDecimal roundedPrice = priceCalculationService.roundPrice(totalPrice);

        // Add the cart and the rounded total price to the model
        model.addAttribute("cart", addToCartFacade.getShoppingCart());
        model.addAttribute("totalPrice", roundedPrice); // Use the rounded total price

        return "cart"; // Return the cart view
    }

    /**
     * Handles the request to add a product to the shopping cart.
     * If the product is available and has sufficient stock, it will be added to the cart.
     * If stock is insufficient, an error message will be shown.
     * 
     * @param id the ID of the product to be added to the cart.
     * @param model the model to add attributes to, which will be passed to the view.
     * @return a redirect to the cart page after attempting to add the product.
     */
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
