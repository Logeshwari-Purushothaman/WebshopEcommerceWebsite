package com.example.webshop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewModel {
    private Long productId;
    private String productName;
    private String userName;
    private String reviewText;
    private Date date;

    // Default constructor
    public ReviewModel() {
    }

    // Constructor with all fields
    public ReviewModel(Long productId, String productName, String userName, String reviewText, String dateString) {
        this.productId = productId;
        this.productName = productName;
        this.userName = userName;
        this.reviewText = reviewText;
        setDate(dateString);
    }

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing exception appropriately
            // For example, you could set it to the current date:
            this.date = new Date();
        }
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Review{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", userName='" + userName + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", date=" + date +
                '}';
    }
}
