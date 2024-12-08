package com.example.webshop;

public class ProductModel {
    private Long id;
    private String name;
    private Double price;
    private String size;
    private String color;
    private String category; // New attribute
    private Integer stock; // Temporary stock field


    // Default constructor (needed for deserialization)
    public ProductModel() {
    }

    // Constructor with parameters
    public ProductModel(long id, String name, double price, String color, String category, String size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
        this.category = category;
        this.stock = stock; // Initialize the stock field

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
