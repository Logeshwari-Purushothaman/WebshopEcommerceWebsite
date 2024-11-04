package com.example.webshop; // Adjust the package name accordingly

public class ProductModel {
    // Attributes
    private Long id;         // Unique identifier for the product
    private String name;     // Name of the product
    private Double price;    // Price of the product
    private String size;     // Size of the product
    private String color;    // Color of the product

    // Constructor
    public ProductModel(Long id, String name, Double price, String size, String color) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
    }

    // Public getter methods for each attribute
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
