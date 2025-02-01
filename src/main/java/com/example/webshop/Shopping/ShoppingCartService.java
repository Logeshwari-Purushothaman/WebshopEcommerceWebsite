package com.example.webshop.Shopping;

import com.example.webshop.PriceCalculationService;
import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import com.example.webshop.inventory.InventoryService;
import com.example.webshop.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class ShoppingCartService {
    
    private final PriceCalculationService priceCalculationService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ShoppingCart shoppingCart;
    private final ProductRepository productRepository;

	public ShoppingCartService(PriceCalculationService priceCalculationService, ProductService productService,
			InventoryService inventoryService, ProductRepository productRepository) {
		this.priceCalculationService = priceCalculationService;
		this.productService = productService;
		this.inventoryService = inventoryService;
		this.productRepository = productRepository;
		this.shoppingCart = new ShoppingCart(priceCalculationService.getDefaultCurrency());
	}

    public void addProductToCart(Long productId) {
    	ProductModel product = productService.getProductById(productId);
            
            if (product != null && product.getStock() > 0) {
                product.setStock(product.getStock() - 1);
                productRepository.save(product);
                
                shoppingCart.getProducts().merge(product, 1, Integer::sum);
                
                // Initialize effectivePrice
                product.setEffectivePrice(product.getPrice());
                
                if (shoppingCart.isVoucherApplied()) {
                    updateItemPrices();
                }
            } else {
                throw new RuntimeException("Not enough stock for product: " + productId);
            }
        }   

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public BigDecimal getOriginalTotalPrice() {
        return shoppingCart.getProducts()
                           .entrySet()
                           .stream()
                           .map(entry -> entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())))
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getEffectiveTotalPrice() {
        return shoppingCart.getProducts()
                           .entrySet()
                           .stream()
                           .map(entry -> entry.getKey().getEffectivePrice().multiply(new BigDecimal(entry.getValue())))
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void applyVoucher() {
        if (!shoppingCart.isVoucherApplied()) {
            shoppingCart.setVoucherApplied(true);
            updateItemPrices();
        }
    }

    public void removeVoucher() {
        if (shoppingCart.isVoucherApplied()) {
            shoppingCart.setVoucherApplied(false);
            updateItemPrices();
        }
    }

    private void updateItemPrices() {
        for (Map.Entry<ProductModel, Integer> entry : shoppingCart.getProducts().entrySet()) {
            ProductModel product = entry.getKey();
            BigDecimal originalPrice = product.getPrice();
            BigDecimal effectivePrice = shoppingCart.isVoucherApplied() 
                ? priceCalculationService.applyPercentageVoucher(originalPrice)
                : originalPrice;
            product.setEffectivePrice(effectivePrice);
        }
    }
}
