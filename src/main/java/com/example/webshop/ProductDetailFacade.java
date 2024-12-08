package com.example.webshop;

import com.example.webshop.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailFacade {

    private final ProductService productService;
    private final InventoryService inventoryService;

    // Constructor-based dependency injection
    @Autowired
    public ProductDetailFacade(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    // Method to get all products with stock
    public List<ProductModel> getAllProductsWithStock() {
        List<ProductModel> products = productService.getAllProducts();
        products.forEach(product -> 
            product.setStock(inventoryService.getStock(product.getId()))
        );
        return products;
    }

    // Method to get product details with stock and sold-out status
    public ProductDetailDTO getProductDetailDTO(Long id) {
        ProductModel product = productService.getProductById(id);
        if (product != null) {
            int stock = inventoryService.getStock(id);
            boolean isSoldOut = stock == 0; // If stock is zero, it is sold out
            return new ProductDetailDTO(product, stock, isSoldOut); // Pass all three parameters to the constructor
        }
        return null;
    }

    // Method to update inventory
    public void updateInventory(Long id, int stock) {
        inventoryService.addStock(id, stock);
    }

    // Method to reduce stock
    public boolean reduceInventory(Long productId, int quantity) {
        try {
            // Attempt to reduce the stock using the inventory service
            return inventoryService.reduceStock(productId, quantity);
        } catch (RuntimeException e) {
            // Handle any errors such as insufficient stock
            System.out.println("Error reducing inventory: " + e.getMessage());
            return false; // Not enough stock
        }
    }

    // Method to add a product with initial stock
    public void addProductWithStock(ProductModel product, int initialStock) {
        productService.addProduct(product);
        inventoryService.initializeStock(product.getId(), initialStock);
    }

    // Method to update a product
    public ProductModel updateProduct(ProductModel updatedProduct) {
        return productService.updateProduct(updatedProduct);
    }

    // Method to delete a product by ID
    public void deleteProductById(Long id) {
        productService.deleteProductById(id);
    }

    // Method to filter products by color
    public List<ProductModel> getProductsByColor(String color) {
        return productService.getProductsByColor(color);
    }

    // Method to filter products by category
    public List<ProductModel> getProductsByCategory(String category) {
        return productService.getProductsByCategory(category);
    }

    // Method to filter products by name
    public List<ProductModel> getProductsByName(String name) {
        return productService.getProductsByName(name);
    }
}
