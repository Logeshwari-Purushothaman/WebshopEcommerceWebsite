package com.example.webshop.Shopping;

import com.example.webshop.PriceCalculationService;
import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import com.example.webshop.inventory.InventoryService;
import com.example.webshop.ProductRepository;  // Import ProductRepository
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


/**
 * Service class that manages the operations related to the shopping cart.
 * This service is responsible for adding products to the cart, updating the stock,
 * and calculating the total price of the cart.
 */
@Service
public class ShoppingCartService {
	
    private final PriceCalculationService priceCalculationService;

    /**
     * The service responsible for managing product-related operations.
     */
    private final ProductService productService;

    /**
     * The service responsible for managing inventory-related operations.
     */
    private final InventoryService inventoryService;

    /**
     * The shopping cart which holds the products and their quantities.
     */
    private final ShoppingCart shoppingCart;

    /**
     * The repository for handling product data persistence.
     */
    private final ProductRepository productRepository;  // Inject ProductRepository

    /**
     * Constructor-based dependency injection for the required services.
     * 
     * @param productService the ProductService used to fetch product details.
     * @param inventoryService the InventoryService used to manage stock operations.
     * @param productRepository the ProductRepository used for data persistence of products.
     */
    public ShoppingCartService(PriceCalculationService priceCalculationService,ProductService productService, 
                               InventoryService inventoryService,
                               ProductRepository productRepository) {  // Add ProductRepository as a parameter
        this.priceCalculationService = priceCalculationService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.productRepository = productRepository;  // Assign ProductRepository
        this.shoppingCart = new ShoppingCart(); // Initialize the shopping cart
    }

    /**
     * Adds a product to the shopping cart after verifying if it is available in stock.
     * If stock is available, the stock is reduced by 1, the product is updated in the repository,
     * and the product is added to the cart. If stock is insufficient, an exception is thrown.
     * 
     * @param productId the ID of the product to add to the cart.
     * @throws RuntimeException if the product is not found or there is insufficient stock.
     */
    public void addProductToCart(Long productId) {
        // Fetch product by ID
        ProductModel product = productService.getProductById(productId);
        
        if (product != null) {
            // Get the available stock
            int stockAvailable = product.getStock();
            
            // Check if stock is available
            if (stockAvailable > 0) {
                // Reduce stock by 1
                product.setStock(stockAvailable - 1);
                
                // Save the updated product back to the repository
                productRepository.save(product);  // Save to the database
                
                // Add the product to the shopping cart (merge logic for quantity)
                shoppingCart.getProducts().merge(product, 1, Integer::sum);
            } else {
                // Throw an exception if not enough stock
                throw new RuntimeException("Not enough stock for product: " + productId);
            }
        } else {
            // Throw an exception if product not found
            throw new RuntimeException("Product not found: " + productId);
        }
    }

    /**
     * Retrieves the current shopping cart.
     * 
     * @return the current shopping cart instance, containing products and their quantities.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Calculates the total price of all products in the shopping cart,
     * and rounds it to 2 decimal places.
     *
     * @return the rounded total price as a BigDecimal
     */
    public BigDecimal getTotalPrice() {
        // Calculate the total price
        BigDecimal total = shoppingCart.getProducts()
                                       .entrySet()
                                       .stream()
                                       .map(entry -> entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())))
                                       .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Round the total price to 2 decimal places
        return priceCalculationService.roundPrice(total);
    }
}

