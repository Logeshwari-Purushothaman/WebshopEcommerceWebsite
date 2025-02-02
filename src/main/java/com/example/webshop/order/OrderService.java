package com.example.webshop.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
	public Order finalizeOrder(BigDecimal effectiveTotalPrice, BigDecimal originalTotalPrice, Long userId, String currency) {
        Long orderId = generateOrderId();
        return new Order(orderId, effectiveTotalPrice, originalTotalPrice, userId, currency);
    }

    private Long generateOrderId() {
        return System.currentTimeMillis();
    }
}
