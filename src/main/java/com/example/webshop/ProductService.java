package com.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;  // Correct import for Page
import org.springframework.data.domain.Pageable;  // Correct import for Pageable

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a product by ID
    public ProductModel getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Add a new product
    public boolean addProduct(ProductModel product) {
        // Save the product to the database
        productRepository.save(product);
        return true;
    }

    // Delete a product by ID
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    // Update a product
    public ProductModel updateProduct(ProductModel updatedProduct) {
        return productRepository.save(updatedProduct);
    }

    // Get products by color
    public List<ProductModel> getProductsByColor(String color) {
        return productRepository.findByColorIgnoreCase(color);
    }

    // Get products by category
    public List<ProductModel> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    // Get products by name
    public List<ProductModel> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name); // Call the new method for partial search
    }

    // Get products by price range
    public List<ProductModel> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    // Save Product to Repository
    public void saveProduct(ProductModel product) {
        productRepository.save(product); // Save product to the database
    }
    
    public Page<ProductModel> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable); // Fetch products with pagination
    }
    
    // Search products by name (case insensitive search)
    public Page<ProductModel> searchProducts(String search, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(search, pageable);
    }
}
