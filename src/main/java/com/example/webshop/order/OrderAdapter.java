package com.example.webshop.order;

import com.example.webshop.Shopping.ShoppingCart;
import com.example.webshop.Shopping.AddToCartFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderAdapter {
	private final OrderFacade orderFacade;
	private final EMailService emailService;
	private final AddToCartFacade addToCartFacade;

	@Autowired
	public OrderAdapter(OrderFacade orderFacade, EMailService emailService, AddToCartFacade addToCartFacade) {
		this.orderFacade = orderFacade;
		this.emailService = emailService;
		this.addToCartFacade = addToCartFacade;
	}

	public Order finalizeOrder(BigDecimal effectiveTotalPrice, BigDecimal originalTotalPrice, String currency) {
	    System.out.println("Debug: OrderAdapter - Finalizing order");
	    System.out.println("Debug: Effective Total Price: " + effectiveTotalPrice);
	    System.out.println("Debug: Original Total Price: " + originalTotalPrice);
	    System.out.println("Debug: Currency: " + currency);

	   // Order order = orderFacade.finalizeOrder(effectiveTotalPrice, originalTotalPrice, currency);
	    return orderFacade.finalizeOrder(effectiveTotalPrice, originalTotalPrice, currency);
	}


}
