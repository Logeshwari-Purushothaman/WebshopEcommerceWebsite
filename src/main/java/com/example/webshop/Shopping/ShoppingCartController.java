package com.example.webshop.Shopping;

import com.example.webshop.PriceCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final AddToCartFacade addToCartFacade;
    private final PriceCalculationService priceCalculationService;

    public ShoppingCartController(AddToCartFacade addToCartFacade, PriceCalculationService priceCalculationService) {
        this.addToCartFacade = addToCartFacade;
        this.priceCalculationService = priceCalculationService;
    }
    
    @PostMapping("/checkout")
    public String initiateCheckout(Model model) {
        ShoppingCart cart = addToCartFacade.getShoppingCart();
        BigDecimal totalPrice = cart.getEffectiveTotalPrice();
        String currency = cart.getCurrency();
        System.out.println("Debug: ShoppingCartController - Initiating checkout");
        System.out.println("Debug: Total Price: " + totalPrice);
        System.out.println("Debug: Currency: " + currency);
        
        // Add these attributes to the model
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("currency", currency);
        
        return "redirect:/order/create";
    }


    @GetMapping("/cart")
    public String getCart(Model model) {
        return getCartInCurrency(addToCartFacade.getCurrentCurrency(), model);
    }
    
    @GetMapping("/cart/{currency}")
    public String getCartInCurrency(@PathVariable String currency, Model model) {
        try {
            ShoppingCart cart = addToCartFacade.getShoppingCart();
            PriceCalculationService.Currency targetCurrency = addToCartFacade.getCurrency(currency);

            // Convert prices if necessary
            if (!targetCurrency.name().equalsIgnoreCase(cart.getCurrency())) {
                addToCartFacade.convertProductPrices(targetCurrency);
            }

            BigDecimal originalTotalPrice = addToCartFacade.getOriginalTotalPrice();
            BigDecimal effectiveTotalPrice = addToCartFacade.getEffectiveTotalPrice();

            model.addAttribute("cart", cart);  // This line was commented out before
            model.addAttribute("originalTotalPrice", originalTotalPrice);
            model.addAttribute("effectiveTotalPrice", effectiveTotalPrice);
            model.addAttribute("currency", targetCurrency.name());
            model.addAttribute("otherCurrency",
                    targetCurrency == PriceCalculationService.Currency.EURO ? "DOLLAR" : "EURO");
            model.addAttribute("voucherPercentage", priceCalculationService.getVoucherPercentage());
            model.addAttribute("totalPrice", cart.getEffectiveTotalPrice());
            model.addAttribute("locale", new java.util.Locale("de", "DE")); // German locale uses comma as decimal separator
            // Remove this line: model.addAttribute("cart", shoppingCart);

            return "cart";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid currency: " + currency);
            return getCart(model);
        }
    }


    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        try {
            addToCartFacade.updateCartItem(productId, quantity);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/shopping/cart";
    }



    @GetMapping("/cart-add/{id}")
    public String addProductToCart(@PathVariable Long id, Model model) {
        try {
            addToCartFacade.addToCart(id);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/shopping/cart";
    }

    @PostMapping("/cart/voucher")
    public String applyVoucher() {
        addToCartFacade.applyVoucher();
        return "redirect:/shopping/cart";
    }

    @PostMapping("/cart/removevoucher")
    public String removeVoucher() {
        addToCartFacade.removeVoucher();
        return "redirect:/shopping/cart";
    }
}
