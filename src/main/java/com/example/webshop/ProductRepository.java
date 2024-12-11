package com.example.webshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;  // Correct import for Page
import org.springframework.data.domain.Pageable;  // Correct import for Pageable
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for handling CRUD operations on ProductModel entities.
 * This interface extends JpaRepository and provides methods for querying and
 * manipulating products in the database. It includes custom queries for filtering products by various attributes.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    /**
     * Custom query to fetch products by color.
     * This method performs a case-insensitive search for products based on their color.
     *
     * @param color The color of the products to be fetched.
     * @return A list of products matching the specified color.
     */
    @Query("SELECT p FROM ProductModel p WHERE p.color = :color")
    List<ProductModel> findByColorIgnoreCase(String color);

    /**
     * Custom query to fetch products by category.
     * This method performs a case-insensitive search for products based on their category.
     *
     * @param category The category of the products to be fetched.
     * @return A list of products matching the specified category.
     */
    List<ProductModel> findByCategoryIgnoreCase(String category);

    /**
     * Custom query to fetch products by name with an exact case-insensitive match.
     * This method performs a case-insensitive search for products based on their name.
     *
     * @param name The name of the products to be fetched.
     * @return A list of products matching the specified name.
     */
    List<ProductModel> findByNameIgnoreCase(String name);

    /**
     * Custom query to fetch products where the name contains the search term (case-insensitive).
     * This method performs a case-insensitive search for products where the name contains the specified search term.
     *
     * @param name The search term to be used in the product names.
     * @return A list of products whose names contain the specified search term.
     */
    List<ProductModel> findByNameContainingIgnoreCase(String name);

    /**
     * Custom query to fetch products within a price range.
     * This method retrieves products whose prices are between the specified minimum and maximum values.
     *
     * @param minPrice The minimum price of the products.
     * @param maxPrice The maximum price of the products.
     * @return A list of products within the specified price range.
     */
    List<ProductModel> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Custom query to fetch products where the name contains the search term (case-insensitive), with pagination support.
     * This method performs a case-insensitive search for products where the name contains the specified search term
     * and returns the results in a paginated form.
     *
     * @param name The search term to be used in the product names.
     * @param pageable The pagination information (e.g., page number, page size).
     * @return A page of products whose names contain the specified search term.
     */
    Page<ProductModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
