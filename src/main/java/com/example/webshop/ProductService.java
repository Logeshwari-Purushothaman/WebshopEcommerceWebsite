package com.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;  // Correct import for Page
import org.springframework.data.domain.Pageable;  // Correct import for Pageable

import java.util.List;

/**
 * Service class for handling business logic related to products.
 * This class provides methods for performing CRUD operations on products, 
 * as well as filtering products based on various criteria like color, category, 
 * name, price, and pagination support.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor for dependency injection of the ProductRepository.
     * The ProductRepository is required to perform CRUD operations on ProductModel entities.
     *
     * @param productRepository The repository used to interact with the product data in the database.
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products from the repository.
     * This method fetches all product records without any filtering or pagination.
     *
     * @return A list of all products in the database.
     */
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     * This method searches for a product based on the provided product ID.
     *
     * @param id The ID of the product to be retrieved.
     * @return The product matching the given ID, or null if not found.
     */
    public ProductModel getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Adds a new product to the repository.
     * This method saves the provided product to the database.
     *
     * @param product The product to be added to the database.
     * @return A boolean indicating whether the product was successfully added (always returns true).
     */
    public boolean addProduct(ProductModel product) {
        productRepository.save(product);
        return true;
    }

    /**
     * Deletes a product by its ID.
     * This method removes a product from the repository based on its ID.
     *
     * @param id The ID of the product to be deleted.
     */
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Updates an existing product in the repository.
     * This method saves an updated version of the product entity.
     *
     * @param updatedProduct The updated product information to be saved.
     * @return The updated product entity.
     */
    public ProductModel updateProduct(ProductModel updatedProduct) {
        return productRepository.save(updatedProduct);
    }

    /**
     * Retrieves products by their color.
     * This method fetches products with the specified color, ignoring case sensitivity.
     *
     * @param color The color of the products to be retrieved.
     * @return A list of products that match the specified color.
     */
    public List<ProductModel> getProductsByColor(String color) {
        return productRepository.findByColorIgnoreCase(color);
    }

    /**
     * Retrieves products by their category.
     * This method fetches products that belong to the specified category, ignoring case sensitivity.
     *
     * @param category The category of the products to be retrieved.
     * @return A list of products that match the specified category.
     */
    public List<ProductModel> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    /**
     * Retrieves products by their name.
     * This method searches for products whose names contain the specified search term, ignoring case sensitivity.
     *
     * @param name The name or search term to be used in product name search.
     * @return A list of products that match the search term in their name.
     */
    public List<ProductModel> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name); // Call the new method for partial search
    }

    /**
     * Retrieves products by a specified price range.
     * This method fetches products whose prices fall between the given minimum and maximum values.
     *
     * @param minPrice The minimum price of the products to be retrieved.
     * @param maxPrice The maximum price of the products to be retrieved.
     * @return A list of products that fall within the specified price range.
     */
    public List<ProductModel> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * Saves a product to the repository.
     * This method persists the product entity to the database.
     *
     * @param product The product to be saved to the database.
     */
    public void saveProduct(ProductModel product) {
        productRepository.save(product); // Save product to the database
    }

    /**
     * Retrieves a paginated list of all products.
     * This method fetches products in a paginated format based on the given pagination parameters.
     *
     * @param pageable The pagination information (page number, size, sorting).
     * @return A page of products based on the provided pagination information.
     */
    public Page<ProductModel> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable); // Fetch products with pagination
    }

    /**
     * Searches for products by name with pagination.
     * This method performs a case-insensitive search for products whose names contain the specified search term
     * and returns the results in a paginated format.
     *
     * @param search The search term to be used in product name search.
     * @param pageable The pagination information (page number, size, sorting).
     * @return A paginated page of products whose names match the search term.
     */
    public Page<ProductModel> searchProducts(String search, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(search, pageable);
    }
}
