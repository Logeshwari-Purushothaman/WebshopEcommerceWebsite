package com.example.webshop.Shopping;

import com.example.webshop.ProductModel;
import com.example.webshop.ProductService;
import com.example.webshop.inventory.InventoryService;
import com.example.webshop.ProductRepository;  // Import ProductRepository
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ShoppingCart shoppingCart;
    private final ProductRepository productRepository;  // Inject ProductRepository

    // Constructor injection of required services
    public ShoppingCartService(ProductService productService, 
                               InventoryService inventoryService,
                               ProductRepository productRepository) {  // Add ProductRepository as a parameter
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.productRepository = productRepository;  // Assign ProductRepository
        this.shoppingCart = new ShoppingCart(); // Initialize the shopping cart
    }

    // Method to add product to cart
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

    // Method to get the shopping cart
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    // Method to calculate the total price of the products in the cart
    public double getTotalPrice() {
        return shoppingCart.getProducts()
                           .entrySet()
                           .stream()
                           .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                           .sum();
    }
}
