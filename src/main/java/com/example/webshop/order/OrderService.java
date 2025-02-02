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

/*
package com.example.webshop.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.usermanagement.UserService;
import java.math.BigDecimal;

@Service
public class OrderService {
    @Autowired
    private UserService userService;

    public Order finalizeOrderWithTotal(BigDecimal total) {
        Long userId = userService.getCurrentUserId();
        return new Order(generateOrderId(), total, total, userId, "USD");
    }

    public Order getRecentOrderForUser(Long userId) {
        return new Order(generateOrderId(), BigDecimal.ZERO, BigDecimal.ZERO, userId, "USD");
    }

    private Long generateOrderId() {
        return System.currentTimeMillis();
    }
}
*/