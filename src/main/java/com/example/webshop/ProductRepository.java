package com.example.webshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    // Custom query to fetch products by color
    @Query("SELECT p FROM ProductModel p WHERE p.color = :color")
    List<ProductModel> findByColorIgnoreCase(String color);

    // Custom query to fetch products by category
    List<ProductModel> findByCategoryIgnoreCase(String category);

    // Custom query to fetch products by name (exact match, case-insensitive)
    List<ProductModel> findByNameIgnoreCase(String name);

    // Custom query to fetch products where the name contains the search term (case-insensitive)
    List<ProductModel> findByNameContainingIgnoreCase(String name);

    // You can also define other utility methods as needed
    List<ProductModel> findByPriceBetween(Double minPrice, Double maxPrice);
}

