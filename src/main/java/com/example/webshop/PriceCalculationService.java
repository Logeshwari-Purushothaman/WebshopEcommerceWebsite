package com.example.webshop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * Service for calculating prices, including rounding operations, currency conversion, and voucher application.
 * This service ensures that prices are rounded to 2 decimal places (cents).
 */
@Service
public class PriceCalculationService {
    
    private final String defaultCurrency;
    private static final BigDecimal EURO_TO_DOLLAR_RATE = new BigDecimal("1.18");
    
    @Value("${app.discount.percentage:10}")
    private BigDecimal voucherPercentage;

    /**
     * Enum representing supported currencies.
     */
    public enum Currency {
        EURO, DOLLAR
    }
    
    public PriceCalculationService(@Value("${app.currency.default}") String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    /**
     * Rounds a given price to 2 decimal places, rounding up from 0.5.
     * 
     * @param price the price to round
     * @return the rounded price as a BigDecimal
     */
    public BigDecimal roundPrice(BigDecimal price) {
        if (price == null) {
            return BigDecimal.ZERO; // Return 0 if the price is null
        }

        // Round the price to two decimal places using HALF_UP rounding mode
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Converts an amount from one currency to another.
     * 
     * @param amount the amount to convert
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     * @return the converted and rounded amount
     */
    public BigDecimal convertCurrency(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        if (fromCurrency == toCurrency) {
            return roundPrice(amount);
        }

        BigDecimal convertedAmount;
        if (fromCurrency == Currency.EURO && toCurrency == Currency.DOLLAR) {
            convertedAmount = amount.multiply(EURO_TO_DOLLAR_RATE);
        } else if (fromCurrency == Currency.DOLLAR && toCurrency == Currency.EURO) {
            convertedAmount = amount.divide(EURO_TO_DOLLAR_RATE, 2, RoundingMode.HALF_UP);
        } else {
            throw new IllegalArgumentException("Unsupported currency conversion");
        }

        return roundPrice(convertedAmount);
    }

    /**
     * Applies a percentage voucher to a given price.
     * 
     * @param price the original price
     * @return the discounted price after applying the voucher
     */
    public BigDecimal applyPercentageVoucher(BigDecimal price) {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountAmount = price.multiply(voucherPercentage).divide(new BigDecimal("100"));
        return roundPrice(price.subtract(discountAmount));
    }
    
    public BigDecimal getVoucherPercentage() {
        return voucherPercentage;
    }
}
