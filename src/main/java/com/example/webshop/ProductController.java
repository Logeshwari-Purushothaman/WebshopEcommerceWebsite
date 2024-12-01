package com.example.webshop;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.webshop.Shopping.ShoppingCartService;

import java.util.List;

@Controller
@RequestMapping("/products") // Base mapping for all product-related endpoints
public class ProductController {

    private final ProductService productService; // Use final for better design
    private final ShoppingCartService shoppingCartService; // Add ShoppingCartService

    // Constructor-based Dependency Injection
    public ProductController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    // Test endpoint for verifying controller functionality
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Controller is working!");
    }

    // Dashboard (index page)
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "index"; // Render the dashboard view
    }

    // Endpoint for the Catalog Page (List of Products)
    @GetMapping("/catalog")
    public String getCatalogPage(@RequestParam(value = "edit", required = false, defaultValue = "false") boolean edit, Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("editMode", edit); // Pass edit mode to Thymeleaf template
        return "catalog"; // Render the catalog.html template
    }

    // Endpoint to delete a product by ID (Non-RESTful endpoint)
    @GetMapping("/product-delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id); // Call service to delete the product
        return "redirect:/products/catalog?edit=true"; // Redirect to catalog page in edit mode
    }

    // Add Product Page
    @GetMapping("/add")
    public String getAddProductPage(Model model) {
        model.addAttribute("product", new ProductModel());  // Empty product for the form
        return "addProduct";  // Render addProduct.html template
    }

    // Add Product (Handle form submission)
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductModel product) {
        productService.addProduct(product); // Add the product to the service
        return "redirect:/products/detail/" + product.getId(); // Redirect to the product detail page
    }

    // Thymeleaf endpoint for displaying the details of a single product (Product Detail Page)
    @GetMapping("/detail/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        ProductModel product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "productDetail";  // Return the productDetail.html template
        } else {
            model.addAttribute("error", "Product not found");  // Add error message to model
            return "error";  // Redirect to an error page or product catalog
        }
    }

    /*// Endpoint to delete a product via the REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<List<ProductModel>> deleteProductById(@PathVariable Long id) {
        List<ProductModel> remainingProducts = productService.deleteProductById(id);
        if (remainingProducts != null) {
            return ResponseEntity.ok(remainingProducts);
        } else {
            return ResponseEntity.status(404).body(null); // Product not found
        }
    }
    */

    // Endpoint to update a product via the REST API
    @PutMapping("/update")
    public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel updatedProduct) {
        ProductModel product = productService.updateProduct(updatedProduct);
        if (product != null) {
            return ResponseEntity.ok(product); // Return updated product
        } else {
            return ResponseEntity.status(404).body(null); // Product not found
        }
    }

    // REST API Endpoint to get all products (JSON response)
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    // REST API Endpoint to filter products by color (JSON response)
    @GetMapping("/filter/color/{color}")
    public ResponseEntity<List<ProductModel>> getProductsByColor(@PathVariable String color) {
        List<ProductModel> filteredProducts = productService.getProductsByColor(color);
        return ResponseEntity.ok(filteredProducts);
    }

    // REST API Endpoint to filter products by category (JSON response)
    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> filteredProducts = productService.getProductsByCategory(category);
        return ResponseEntity.ok(filteredProducts);
    }

    // REST API Endpoint to filter products by name (JSON response)
    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<ProductModel>> getProductsByName(@PathVariable String name) {
        List<ProductModel> filteredProducts = productService.getProductsByName(name);
        return ResponseEntity.ok(filteredProducts);
    }
}
