package com.example.webshop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Service for calculating prices, including rounding operations.
 * This service ensures that prices are rounded to 2 decimal places (cents).
 */
@Service
public class PriceCalculationService {

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
}
