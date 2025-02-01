package com.example.webshop.Shopping;

import com.example.webshop.*;
import com.example.webshop.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;


/**
 * Facade service that handles operations related to adding products to the shopping cart.
 * This service interacts with the product, shopping cart, and inventory services to manage
 * product availability and cart updates.
 */
@Service
public class AddToCartFacade {

    private final InventoryService inventoryService;
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final PriceCalculationService priceCalculationService;

    /**
     * Constructor that injects dependencies for product, shopping cart, inventory, and price calculation services.
     *
     * @param inventoryService The service responsible for managing inventory (stock levels).
     * @param shoppingCartService The service responsible for handling shopping cart operations.
     * @param productService The service responsible for managing product information.
     * @param priceCalculationService The service responsible for price calculations and voucher application.
     */
    @Autowired
    public AddToCartFacade(InventoryService inventoryService, ShoppingCartService shoppingCartService, 
                           ProductService productService, PriceCalculationService priceCalculationService) {
        this.inventoryService = inventoryService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.priceCalculationService = priceCalculationService;
    }
    
    
    /**
     * Gets the current currency of the shopping cart.
     *
     * @return The current currency as a string.
     */
    public String getCurrentCurrency() {
        return shoppingCartService.getShoppingCart().getCurrency();
    }

    /**
     * Updates the currency of the shopping cart.
     *
     * @param currency The new currency to set.
     */
    public void updateCartCurrency(String currency) {
        shoppingCartService.getShoppingCart().setCurrency(currency);
    }
    
    
    /**
     * Converts the total price to the specified currency.
     *
     * @param targetCurrency The currency to convert to (EURO or DOLLAR).
     * @return The converted total price.
    */
    
    public BigDecimal convertTotalPrice(PriceCalculationService.Currency targetCurrency, boolean isEffective) {
        ShoppingCart cart = shoppingCartService.getShoppingCart();
        BigDecimal totalPrice = isEffective ? getEffectiveTotalPrice() : getOriginalTotalPrice();
        PriceCalculationService.Currency sourceCurrency = getCurrency(cart.getCurrency());
        return priceCalculationService.convertCurrency(totalPrice, sourceCurrency, targetCurrency);
    }
    
    public void convertProductPrices(PriceCalculationService.Currency targetCurrency) {
        ShoppingCart cart = shoppingCartService.getShoppingCart();
        PriceCalculationService.Currency sourceCurrency = getCurrency(cart.getCurrency());
        
        for (Map.Entry<ProductModel, Integer> entry : cart.getProducts().entrySet()) {
            ProductModel product = entry.getKey();
            BigDecimal originalPrice = product.getPrice();
            BigDecimal convertedPrice = priceCalculationService.convertCurrency(originalPrice, sourceCurrency, targetCurrency);
            product.setPrice(convertedPrice);
            
            BigDecimal originalEffectivePrice = product.getEffectivePrice();
            BigDecimal convertedEffectivePrice = priceCalculationService.convertCurrency(originalEffectivePrice, sourceCurrency, targetCurrency);
            product.setEffectivePrice(convertedEffectivePrice);
        }

        
        cart.setCurrency(targetCurrency.name());
    }


    private BigDecimal calculateTotalPrice(ShoppingCart cart) {
        return cart.getProducts().entrySet().stream()
            .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PriceCalculationService.Currency getCurrency(String currency) {
        if (currency.equalsIgnoreCase("EURO") || currency.equalsIgnoreCase("EUR")) {
            return PriceCalculationService.Currency.EURO;
        } else if (currency.equalsIgnoreCase("DOLLAR") || currency.equalsIgnoreCase("USD")) {
            return PriceCalculationService.Currency.DOLLAR;
        } else {
            throw new IllegalArgumentException("Invalid currency: " + currency);
        }
    }
    
    public void updateCartItem(Long productId, int quantity) {
        ShoppingCart cart = shoppingCartService.getShoppingCart();
        ProductModel product = productService.getProductById(productId);
        
        if (product == null) {
            throw new RuntimeException("Product not found: " + productId);
        }
        
        if (quantity <= 0) {
            cart.getProducts().remove(product);
        } else {
            if (quantity > product.getStock()) {
                throw new RuntimeException("Not enough stock for product: " + productId);
            }
            cart.getProducts().put(product, quantity);
        }
    }
    
    private void updateCartTotals(ShoppingCart cart) {
        BigDecimal originalTotal = BigDecimal.ZERO;
        BigDecimal effectiveTotal = BigDecimal.ZERO;
        
        for (Map.Entry<ProductModel, Integer> entry : cart.getProducts().entrySet()) {
            ProductModel product = entry.getKey();
            Integer quantity = entry.getValue();
            
            originalTotal = originalTotal.add(product.getPrice().multiply(new BigDecimal(quantity)));
            effectiveTotal = effectiveTotal.add(product.getEffectivePrice().multiply(new BigDecimal(quantity)));
        }
        
        cart.setOriginalTotalPrice(originalTotal);
        cart.setEffectiveTotalPrice(effectiveTotal);
    }

    /**
     * Adds a product to the shopping cart if sufficient stock is available. If the product is
     * out of stock, an exception is thrown.
     *
     * @param productId The ID of the product to be added to the cart.
     * @return The updated shopping cart after the product is added.
     * @throws RuntimeException if the product is not found or there is insufficient stock.
     */
    public ShoppingCart addToCart(Long productId) {
        ProductModel product = productService.getProductById(productId);

        // Check if the product exists and has stock available
        if (product == null || product.getStock() <= 0) {
            throw new RuntimeException("Not enough stock for product: " + productId);
        }
        else {
            shoppingCartService.addProductToCart(productId); // Add product to the cart
        }
        
        // Return the updated cart
        return shoppingCartService.getShoppingCart();
    }
    
    /**
     * Retrieves the current shopping cart for the user.
     *
     * @return The current shopping cart.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    /**
     * Calculates and returns the original total price of all products in the shopping cart.
     *
     * @return The original total price of the items in the cart.
     */
    public BigDecimal getOriginalTotalPrice() {
        return shoppingCartService.getOriginalTotalPrice();
    }

    /**
     * Calculates and returns the effective total price of all products in the shopping cart,
     * taking into account any applied vouchers.
     *
     * @return The effective total price of the items in the cart.
     */
    public BigDecimal getEffectiveTotalPrice() {
        return shoppingCartService.getEffectiveTotalPrice();
    }

    /**
     * Applies a voucher to the shopping cart.
     */
    public void applyVoucher() {
        shoppingCartService.applyVoucher();
    }

    /**
     * Removes the applied voucher from the shopping cart.
     */
    public void removeVoucher() {
        shoppingCartService.removeVoucher();
    }

    /**
     * Gets the voucher percentage.
     *
     * @return The voucher percentage as a BigDecimal.
     */
    public BigDecimal getVoucherPercentage() {
        return priceCalculationService.getVoucherPercentage();
    }
}
