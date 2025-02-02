package com.example.webshop.order;

import org.springframework.stereotype.Service;
import com.example.usermanagement.UserService;
import java.math.BigDecimal;

@Service
public class OrderFacade {
	private final OrderService orderService;
	private final UserService userService;

	public OrderFacade(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	public Order finalizeOrder(BigDecimal effectiveTotalPrice, BigDecimal originalTotalPrice, String currency) {
		System.out.println("Debug: OrderFacade - Creating order");
		System.out.println("Debug: Currency: " + currency);

		Long orderId = generateOrderId();
		System.out.println("Debug: Generated Order ID: " + orderId);

		Long userId = userService.getCurrentUserId();
		System.out.println("Debug: Current User ID: " + userId);

		return new Order(orderId, effectiveTotalPrice, originalTotalPrice, userId, currency);
	}

	private Long generateOrderId() {
		// This is a simple example. In a real application, you might want to use a more
		// sophisticated method
		return System.currentTimeMillis();
	}
}
