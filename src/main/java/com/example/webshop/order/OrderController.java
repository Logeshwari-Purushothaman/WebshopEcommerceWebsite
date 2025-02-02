package com.example.webshop.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webshop.Shopping.AddToCartFacade;
import com.example.webshop.Shopping.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderAdapter orderAdapter;
    private final AddToCartFacade addToCartFacade;

    @Autowired
    public OrderController(OrderAdapter orderAdapter, AddToCartFacade addToCartFacade) {
        this.orderAdapter = orderAdapter;
        this.addToCartFacade = addToCartFacade;
    }

    @GetMapping("/orderSuccess")
    public String showOrderSuccess() {
        return "orderSuccess";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("totalPrice") BigDecimal totalPrice, Model model) {
        logger.debug("Checkout initiated");

        ShoppingCart cart = addToCartFacade.getShoppingCart();
        logger.debug("Cart object: {}", cart);

        if (cart == null || cart.getProducts().isEmpty()) {
            model.addAttribute("error", "Your shopping cart is empty.");
            return "cart"; // Redirect back to cart page with error message
        }

        // Ensure effectiveTotalPrice, originalTotalPrice, and currency are correctly fetched from the cart
        BigDecimal effectiveTotalPrice = cart.getEffectiveTotalPrice();
        BigDecimal originalTotalPrice = cart.getOriginalTotalPrice();
        String currency = cart.getCurrency();

        logger.debug("Effective Total Price from cart: {}", effectiveTotalPrice);
        logger.debug("Original Total Price from cart: {}", originalTotalPrice);
        logger.debug("Currency from cart: {}", currency);
        System.out.println("Debug: Finalizing order in OrderController");
        System.out.println("Debug: Effective Total Price: " + effectiveTotalPrice);
        System.out.println("Debug: Original Total Price: " + originalTotalPrice);
        System.out.println("Debug: Currency: " + currency);


        // Finalize the order using the OrderAdapter
        Order order = orderAdapter.finalizeOrder(effectiveTotalPrice, originalTotalPrice, currency);

        // Add the final order to the model
        model.addAttribute("order", order);

        return "orderSuccess";
    }
}
