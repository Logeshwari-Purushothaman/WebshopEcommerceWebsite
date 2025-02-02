package com.example.webshop.order;

import java.math.BigDecimal;

public class Order {
	private Long id;
	private BigDecimal effectiveTotalPrice;
	private Long userId;
	private String currency;
	private BigDecimal originalTotalPrice;

    public Order(Long id, BigDecimal effectiveTotalPrice, BigDecimal originalTotalPrice, Long userId, String currency) {
		this.id = id;
		this.effectiveTotalPrice = effectiveTotalPrice;
	    this.originalTotalPrice = originalTotalPrice;
		this.userId = userId;
		this.currency = currency;
		System.out.println("Debug: Order constructor - Set total price to: " + this.effectiveTotalPrice);
	}

	// Getters
	public Long getId() {	
		return id;
	}
	
	public BigDecimal getTotalPrice() {
	    return this.effectiveTotalPrice;
	}

	public BigDecimal getEffectiveTotalPrice() {
		System.out.println("Debug: getTotalPrice called, returning: " + effectiveTotalPrice); // Add this debug line
		return effectiveTotalPrice;
	}

	public Long getUserId() {
		return userId;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getOriginalTotalPrice() {
		return originalTotalPrice;
	}
}
