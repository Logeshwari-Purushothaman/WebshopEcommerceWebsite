package com.example.webshop;

public class ProductDetailDTO {

    private ProductModel product;
    private int stock;
    private boolean isSoldOut;

    public ProductDetailDTO(ProductModel product, int stock, boolean isSoldOut) {
        this.product = product;
        this.stock = stock;
        this.isSoldOut = isSoldOut;
    }

    // Getters and Setters

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
}
