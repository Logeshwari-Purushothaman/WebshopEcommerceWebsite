package com.example.webshop; 

public class ProductModel {
    private Long id;
    private String name;
    private Double price;
    private String size;
    private String color;
    private String category; // New attribute

    // Constructor
    public ProductModel(long id, String name, double price,String color, String category,String size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
        this.category = category; // Initialize new attribute
    }
    
    // Constructor without ID (for new products)
    public ProductModel(String name, double price, String color, String category, String size) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
        this.category = category;
    }
    
 // Setters (if needed)
    public void setId(Long id) {
        this.id = id;
    }

    // Getters
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

    public String getCategory() {
        return category; // New getter
    }
}
