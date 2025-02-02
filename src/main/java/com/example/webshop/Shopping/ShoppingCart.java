package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    
    private String currency;

    private final Map<ProductModel, Integer> products = new HashMap<>();
    private boolean voucherApplied = false;
    private BigDecimal originalTotalPrice = BigDecimal.ZERO;
    private BigDecimal effectiveTotalPrice = BigDecimal.ZERO;
    private Long orderId;
    private BigDecimal totalPrice;


    public ShoppingCart(String currency) {
        this.currency = currency; 
        this.orderId = null; // Initialize as null

    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void addProduct(ProductModel product, int quantity) {
        products.merge(product, quantity, Integer::sum);
        updateTotalPrices();
    }

    public void removeProduct(ProductModel product) {
        products.remove(product);
        updateTotalPrices();
    }

    private void updateTotalPrices() {
        originalTotalPrice = products.entrySet().stream()
            .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        effectiveTotalPrice = products.entrySet().stream()
            .map(entry -> entry.getKey().getEffectivePrice().multiply(BigDecimal.valueOf(entry.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        products.clear();
        voucherApplied = false;
        originalTotalPrice = BigDecimal.ZERO;
        effectiveTotalPrice = BigDecimal.ZERO;
    }
    
    public int getTotalItems() {
        return products.values().stream().mapToInt(Integer::intValue).sum();
    }

    
    public Map<ProductModel, Integer> getProducts() {
        return products;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isVoucherApplied() {
        return voucherApplied;
    }

    public void setVoucherApplied(boolean voucherApplied) {
        this.voucherApplied = voucherApplied;
    }

    public BigDecimal getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
        this.originalTotalPrice = originalTotalPrice;
    }

    public BigDecimal getEffectiveTotalPrice() {
        System.out.println("Debug: ShoppingCart - getEffectiveTotalPrice called");
        System.out.println("Debug: effectiveTotalPrice: " + effectiveTotalPrice);
        return effectiveTotalPrice;
    }


    public void setEffectiveTotalPrice(BigDecimal effectiveTotalPrice) {
        this.effectiveTotalPrice = effectiveTotalPrice;
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
