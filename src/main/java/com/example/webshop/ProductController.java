package com.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.webshop.inventory.InventoryService;
import com.example.webshop.Shopping.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products") // Base mapping for all product-related endpoints
public class ProductController {

    private final ProductDetailFacade productDetailFacade; // Use ProductDetailFacade
    private final ShoppingCartService shoppingCartService; // Add ShoppingCartService
   

    // Constructor-based Dependency Injection
    @Autowired
    public ProductController(ProductDetailFacade productDetailFacade, ShoppingCartService shoppingCartService) {
        this.productDetailFacade = productDetailFacade;
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

    // Catalog Page (List of Products with Inventory)
    @GetMapping("/catalog")
    public String getCatalogPage(@RequestParam(defaultValue = "false") boolean edit,Model model) {
        List<ProductModel> products = productDetailFacade.getAllProductsWithStock(); // Get products with stock from facade
        model.addAttribute("products", products);
        model.addAttribute("editMode", edit); // Pass editMode flag to the view
        return "catalog"; // Render catalog.html
    }

    // REST API Endpoint to get all products with stock (JSON response)
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProductsWithStock() {
        List<ProductModel> products = productDetailFacade.getAllProductsWithStock(); // Use facade to get products with stock
        return ResponseEntity.ok(products);
    }
    
    
     // Endpoint to display the details of a product
    @GetMapping("/products/detail/{id}")
    public String getProductDetail(@PathVariable("id") Long productId, Model model) {
        ProductDetailDTO productDetailDTO = productDetailFacade.getProductDetailDTO(productId);

        if (productDetailDTO != null) {
            model.addAttribute("productDetailDTO", productDetailDTO);
            return "ProductDetail"; // Return the product detail page
        } else {
            model.addAttribute("error", "Product not found");
            return "error"; // Return error page if product is not found
        }
    }

    // Product Detail Page (With Inventory)
    @GetMapping("/detail/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        ProductDetailDTO productDetailDTO = productDetailFacade.getProductDetailDTO(id); // Use facade for product details
        if (productDetailDTO != null) {
            model.addAttribute("productDetailDTO", productDetailDTO); // Add product details and stock to model
            return "productDetail"; // Render productDetail.html
        } else {
            model.addAttribute("error", "Product not found");
            return "error";
        }
    }

    // Update Inventory for a Product
    @PostMapping("/inventory/update/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable Long id, @RequestParam int stock) {
        productDetailFacade.updateInventory(id, stock); // Delegate to facade
        return ResponseEntity.ok("Inventory updated successfully!");
    }

    // Reduce Stock for a Product
    @PostMapping("/inventory/reduce/{id}")
    public ResponseEntity<String> reduceInventory(@PathVariable Long id, @RequestParam int quantity) {
        boolean success = productDetailFacade.reduceInventory(id, quantity); // Delegate to facade
        if (success) {
            return ResponseEntity.ok("Stock reduced successfully!");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock available!");
        }
    }

    // Add Product (with Initial Stock)
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductModel product, @RequestParam int initialStock) {
        productDetailFacade.addProductWithStock(product, initialStock); // Delegate to facade
        return "redirect:/products/detail/" + product.getId(); // Redirect to the product detail page
    }

    // Add Product Page
    @GetMapping("/add")
    public String getAddProductPage(Model model) {
        model.addAttribute("product", new ProductModel());  // Empty product for the form
        return "addProduct";  // Render addProduct.html template
    }

    // Endpoint to update a product via the REST API
    @PutMapping("/update")
    public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel updatedProduct) {
        ProductModel product = productDetailFacade.updateProduct(updatedProduct); // Delegate to facade
        if (product != null) {
            return ResponseEntity.ok(product); // Return updated product
        } else {
            return ResponseEntity.status(404).body(null); // Product not found
        }
    }

    // Endpoint to delete a product by ID (Non-RESTful endpoint)
    @GetMapping("/product-delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productDetailFacade.deleteProductById(id); // Delegate to facade
        return "redirect:/products/catalog?edit=true"; // Redirect to catalog page in edit mode
    }
    
    // REST API Endpoint to filter products by color (JSON response)
    @GetMapping("/filter/color/{color}")
    public ResponseEntity<List<ProductModel>> getProductsByColor(@PathVariable String color) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByColor(color); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }

    // REST API Endpoint to filter products by category (JSON response)
    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByCategory(category); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }

    // REST API Endpoint to filter products by name (JSON response)
    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<ProductModel>> getProductsByName(@PathVariable String name) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByName(name); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }
}
