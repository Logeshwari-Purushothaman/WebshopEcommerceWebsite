package com.example.webshop;

/**
 * Data Transfer Object (DTO) representing the details of a product, including its stock information and availability status.
 * This class encapsulates the product details, stock quantity, and whether the product is sold out.
 */
public class ProductDetailDTO {

    /** The product model associated with this DTO. */
    private ProductModel product;

    /** The current stock quantity of the product. */
    private int stock;

    /** Indicates whether the product is sold out or not. */
    private boolean isSoldOut;

    /**
     * Constructor for initializing a ProductDetailDTO instance with the provided product, stock, and sold-out status.
     * 
     * @param product The product model representing the product details.
     * @param stock The quantity of the product currently in stock.
     * @param isSoldOut A flag indicating whether the product is sold out.
     */
    public ProductDetailDTO(ProductModel product, int stock, boolean isSoldOut) {
        this.product = product;
        this.stock = stock;
        this.isSoldOut = isSoldOut;
    }

    /**
     * Gets the product associated with this DTO.
     * 
     * @return The product model representing the product details.
     */
    public ProductModel getProduct() {
        return product;
    }

    /**
     * Sets the product for this DTO.
     * 
     * @param product The product model representing the product details.
     */
    public void setProduct(ProductModel product) {
        this.product = product;
    }

    /**
     * Gets the current stock quantity of the product.
     * 
     * @return The stock quantity of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity for the product.
     * 
     * @param stock The new stock quantity of the product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Checks whether the product is sold out.
     * 
     * @return True if the product is sold out, otherwise false.
     */
    public boolean isSoldOut() {
        return isSoldOut;
    }

    /**
     * Sets the sold-out status for the product.
     * 
     * @param soldOut A flag indicating whether the product is sold out.
     */
    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
}
