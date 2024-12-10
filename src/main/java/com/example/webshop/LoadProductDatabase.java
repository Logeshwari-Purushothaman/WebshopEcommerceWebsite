package com.example.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Configuration
public class LoadProductDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadProductDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            // Sample products for preloading the database
            List<ProductModel> products = List.of(
                new ProductModel("T-Shirt", 19.99, "Red", "Clothing", "M", 100),
                new ProductModel("Jeans", 39.99, "Blue", "Clothing", "L", 50),
                new ProductModel("Sneakers", 59.99, "Black", "Footwear", "10", 30),
                new ProductModel("Hat", 15.99, "Green", "Accessories", "One Size", 200),
                new ProductModel("Jacket", 89.99, "Gray", "Clothing", "L", 150),
                new ProductModel("T-Shirt", 21.99, "Black", "Clothing", "L", 120),
                new ProductModel("Socks", 9.99, "White", "Clothing", "M", 300),
                new ProductModel("Scarf", 19.99, "Red", "Accessories", "XL", 80),
                new ProductModel("Sweater", 29.99, "Blue", "Clothing", "M", 75),
                new ProductModel("Gloves", 12.99, "Black", "Accessories", "XXL", 150)
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
