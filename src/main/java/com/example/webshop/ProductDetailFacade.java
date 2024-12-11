package com.example.webshop;

import com.example.webshop.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade class for handling product details, including fetching product information, updating inventory, and managing products.
 * This class acts as an intermediary between the service layer and the repository layer, delegating tasks to the relevant services.
 */
@Service
public class ProductDetailFacade {

    /** The product service responsible for managing product-related operations. */
    private final ProductService productService;

    /** The product repository used for interacting with the database for product-related queries. */
    private final ProductRepository productRepository;

    /** The inventory service used for managing stock and inventory-related operations. */
    private final InventoryService inventoryService;

    /**
     * Constructor-based dependency injection for the services and repository required by this facade.
     * 
     * @param productService The product service for managing product-related operations.
     * @param inventoryService The inventory service for managing stock-related operations.
     * @param productRepository The repository used for product data access.
     */
    @Autowired
    public ProductDetailFacade(ProductService productService, InventoryService inventoryService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository; // Initialize repository
        this.inventoryService = inventoryService;
    }

    /**
     * Retrieves all products along with their stock information.
     * 
     * @return A list of all products with stock details.
     */
    public List<ProductModel> getAllProductsWithStock() {
        List<ProductModel> products = productRepository.findAll(); // Fetch all products from the repository
        return products;
    }

    /**
     * Retrieves the product details along with stock and sold-out status based on the product ID.
     * 
     * @param id The ID of the product to fetch details for.
     * @return A ProductDetailDTO containing product details, stock, and sold-out status, or null if not found.
     */
    public ProductDetailDTO getProductDetailDTO(Long id) {
        ProductModel product = productService.getProductById(id);
        if (product != null) {
            int stock = product.getStock();
            boolean isSoldOut = stock == 0; // Determine if the product is sold out based on stock
            return new ProductDetailDTO(product, stock, isSoldOut); // Return the product details DTO
        }
        return null;
    }

    /**
     * Updates the inventory stock for a given product.
     * 
     * @param id The ID of the product to update the inventory for.
     * @param stock The amount of stock to add to the inventory.
     */
    public void updateInventory(Long id, int stock) {
        inventoryService.addStock(id, stock); // Add stock to the inventory via the inventory service
    }

    /**
     * Reduces the inventory stock for a product by a given quantity.
     * 
     * @param productId The ID of the product to reduce stock for.
     * @param quantity The amount of stock to reduce.
     * @return True if the stock was successfully reduced, false otherwise (e.g., insufficient stock).
     */
    public boolean reduceInventory(Long productId, int quantity) {
        try {
            // Attempt to reduce stock using the inventory service
            return inventoryService.reduceStock(productId, quantity);
        } catch (RuntimeException e) {
            // Handle any errors (e.g., insufficient stock)
            System.out.println("Error reducing inventory: " + e.getMessage());
            return false; // Return false if stock reduction fails
        }
    }

    /**
     * Adds a new product to the system with initial stock.
     * 
     * @param product The product model to add to the system.
     * @param initialStock The initial stock quantity for the new product.
     */
    public void addProductWithStock(ProductModel product, int initialStock) {
        productService.addProduct(product); // Add the product via the product service
        inventoryService.initializeStock(product.getId(), initialStock); // Initialize the product stock
    }

    /**
     * Updates an existing product with new details.
     * 
     * @param updatedProduct The updated product model to save.
     * @return The updated product model after saving.
     */
    public ProductModel updateProduct(ProductModel updatedProduct) {
        return productService.updateProduct(updatedProduct); // Delegate product update to product service
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete.
     */
    public void deleteProductById(Long id) {
        productService.deleteProductById(id); // Delete the product via the product service
    }

    /**
     * Retrieves a list of products filtered by color.
     * 
     * @param color The color of the products to filter by.
     * @return A list of products that match the specified color.
     */
    public List<ProductModel> getProductsByColor(String color) {
        return productRepository.findByColorIgnoreCase(color); // Retrieve products by color, case-insensitive
    }

    /**
     * Retrieves a list of products filtered by category.
     * 
     * @param category The category of the products to filter by.
     * @return A list of products that match the specified category.
     */
    public List<ProductModel> getProductsByCategory(String category) {
        return productService.getProductsByCategory(category); // Fetch products by category via product service
    }

    /**
     * Retrieves a list of products filtered by name.
     * 
     * @param name The name of the products to filter by.
     * @return A list of products that match the specified name.
     */
    public List<ProductModel> getProductsByName(String name) {
        return productService.getProductsByName(name); // Fetch products by name via product service
    }

    /**
     * Adds a product to the system.
     * 
     * @param product The product model to save.
     */
    public void addProduct(ProductModel product) {
        productService.saveProduct(product); // Delegate the saving of product to product service
    }
}
