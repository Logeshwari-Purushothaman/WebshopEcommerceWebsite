package com.example.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;
import java.math.BigDecimal;


/**
 * Configuration class for preloading the product database with sample data.
 * <p>
 * This class is responsible for populating the database with sample product data 
 * during application startup and updating random stock values for products.
 * </p>
 */
@Configuration
public class LoadProductDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadProductDatabase.class);

    /**
     * Bean definition for initializing the product database.
     * <p>
     * This method preloads a list of sample products into the database if they don't already exist, 
     * based on a combination of name, color, and size. Additionally, it sets a random stock value 
     * for each product.
     * </p>
     *
     * @param repository the {@link ProductRepository} used for database operations
     * @return a {@link CommandLineRunner} that executes during application startup
     */
    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            // Sample products for preloading the database
            List<ProductModel> products = List.of(
                new ProductModel("T-Shirt", new BigDecimal(19.99), "Red", "Clothing", "M", 100),
                new ProductModel("Jeans", new BigDecimal(39.99), "Blue", "Clothing", "L", 50),
                new ProductModel("Sneakers", new BigDecimal(59.99), "Black", "Footwear", "10", 30),
                new ProductModel("Hat", new BigDecimal(15.99), "Green", "Accessories", "One Size", 200),
                new ProductModel("Jacket", new BigDecimal(89.99), "Gray", "Clothing", "L", 150),
                new ProductModel("T-Shirt", new BigDecimal(21.99), "Black", "Clothing", "L", 120),
                new ProductModel("Socks", new BigDecimal(9.99), "White", "Clothing", "M", 300),
                new ProductModel("Scarf", new BigDecimal(19.99), "Red", "Accessories", "XL", 80),
                new ProductModel("Sweater", new BigDecimal(29.99), "Blue", "Clothing", "M", 75),
                new ProductModel("Gloves", new BigDecimal(12.99), "Black", "Accessories", "XXL", 150)
            );

            // Get all products from the repository for comparison
            List<ProductModel> existingProducts = repository.findAll();

            // Iterate over the products and save them to the database
            for (ProductModel product : products) {
                // Check if product already exists in the repository by a combination of name, color, and size
                if (existingProducts.stream().noneMatch(p ->
                        p.getName().equalsIgnoreCase(product.getName()) &&
                        p.getColor().equalsIgnoreCase(product.getColor()) &&
                        p.getSize().equalsIgnoreCase(product.getSize()))) {
                    // Save the product to the database
                    repository.save(product);
                    logger.info("Saved product: {}", product.getName());
                } else {
                    logger.info("Product already exists, skipping: {}", product.getName());
                }
            }

            // Set random stock for each product if stock is not set
            Random random = new Random();
            repository.findAll().forEach(product -> {
                int randomStock = random.nextInt(500); // Random stock between 0 and 500
                product.setStock(randomStock);
                repository.save(product);
                logger.info("Updated stock for product {}: {}", product.getName(), randomStock);
            });

            logger.info("Preloaded database with sample products.");
        };
    }
}
