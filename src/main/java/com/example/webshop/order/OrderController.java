package com.example.webshop.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webshop.Shopping.AddToCartFacade;
import com.example.webshop.Shopping.ShoppingCart;

@Controller
public class OrderController {
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
	public String checkout(Model model) {
	    System.out.println("Debug: Checkout initiated");
	    ShoppingCart cart = addToCartFacade.getShoppingCart();
	    BigDecimal effectiveTotalPrice = cart.getEffectiveTotalPrice();
	    BigDecimal originalTotalPrice = cart.getOriginalTotalPrice();
	    String currency = cart.getCurrency();

	    System.out.println("Debug: OrderController - Checkout initiated");
	    System.out.println("Debug: Effective Total Price from cart: " + effectiveTotalPrice);
	    System.out.println("Debug: Original Total Price from cart: " + originalTotalPrice);
	    System.out.println("Debug: Currency from cart: " + currency);

	    Order order = orderAdapter.finalizeOrder(effectiveTotalPrice, originalTotalPrice, currency);

	    model.addAttribute("order", order);
	    model.addAttribute("originalTotalPrice", originalTotalPrice);

	    return "orderSuccess";
	}


}
