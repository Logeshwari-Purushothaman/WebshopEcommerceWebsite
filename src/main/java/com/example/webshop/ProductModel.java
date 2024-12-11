package com.example.webshop;

import java.util.Objects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;


/**
 * Entity class representing a product in the webshop system.
 * This class is mapped to a database table and contains product-related information such as
 * name, price, size, color, category, and stock.
 */
@Entity
public class ProductModel {

    /** The unique identifier for each product in the system. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the product. */
    private String name;

    /** The price of the product. */
    private BigDecimal price;

    /** The size of the product. */
    private String size;

    /** The color of the product. */
    private String color;

    /** The category to which the product belongs. */
    private String category;

    /** The current stock quantity of the product. */
    private Integer stock;

    /** Default constructor for the ProductModel entity. */
    public ProductModel() {
    }

    /**
     * Constructor for creating a new product with specific details.
     * 
     * @param name The name of the product.
     * @param price The price of the product.
     * @param color The color of the product.
     * @param category The category of the product.
     * @param size The size of the product.
     * @param stock The stock quantity of the product.
     */
    public ProductModel(String name, BigDecimal price, String color, String category, String size, Integer stock) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
        this.category = category;
        this.stock = stock;
    }

    /**
     * Gets the ID of the product.
     * 
     * @return The unique ID of the product.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     * 
     * @param id The unique ID to set for the product.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     * 
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * 
     * @param name The name to set for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product.
     * 
     * @return The price of the product.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * 
     * @param price The price to set for the product.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the size of the product.
     * 
     * @return The size of the product.
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the size of the product.
     * 
     * @param size The size to set for the product.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Gets the color of the product.
     * 
     * @return The color of the product.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the product.
     * 
     * @param color The color to set for the product.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the category of the product.
     * 
     * @return The category of the product.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the product.
     * 
     * @param category The category to set for the product.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the stock quantity of the product.
     * 
     * @return The stock quantity of the product.
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     * 
     * @param stock The stock quantity to set for the product.
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * Compares the current product with another product for equality based on product ID.
     * 
     * @param o The object to compare with the current product.
     * @return True if both products have the same ID, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return id != null && id.equals(that.id);  // Compare based on unique product ID
    }

    /**
     * Generates a hash code for the product based on the product ID.
     * 
     * @return A hash code representing the product, based on its ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);  // Generate hash code based on product ID
    }
}
