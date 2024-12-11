package com.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;

import com.example.webshop.Shopping.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;  // Correct import for Page
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;  // Correct import for Pageable
import org.springframework.data.web.PageableDefault;


import java.util.List;

/**
 * ProductController handles all product-related functionality for the webshop.
 * It provides endpoints for viewing, adding, updating, deleting, and filtering products.
 * It also supports paginated product listings and product detail views.
 * 
 * Dependencies:
 * - ProductDetailFacade: Provides methods for interacting with product data (adding, updating, deleting, etc.).
 * - ShoppingCartService: Manages the shopping cart functionality.
 * - ProductService: Provides services related to fetching products and pagination.
 */
@Controller
@RequestMapping("/products") // Base mapping for all product-related endpoints
public class ProductController {

    private final ProductDetailFacade productDetailFacade; // Facade for product detail operations
    private final ShoppingCartService shoppingCartService; // Service for shopping cart operations
    private final ProductService productService; // Service for fetching and managing products

    /**
     * Constructor-based Dependency Injection to initialize the necessary services.
     * 
     * @param productDetailFacade The facade to handle product operations.
     * @param shoppingCartService The service to handle shopping cart operations.
     * @param productService The service to manage product data.
     */
    @Autowired
    public ProductController(ProductDetailFacade productDetailFacade, ShoppingCartService shoppingCartService, ProductService productService) {
        this.productDetailFacade = productDetailFacade;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    /**
     * Test endpoint to verify controller functionality.
     * 
     * @return A ResponseEntity with a message indicating the controller is working.
     */
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Controller is working!");
    }

    /**
     * Returns the dashboard (index) page of the webshop.
     * 
     * @return The name of the view to render.
     */
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "index"; // Render the dashboard view
    }
    
    /**
     * Displays the "Add Product" page where a user can input product details.
     * 
     * @param model The model to bind the ProductModel for form binding.
     * @return The view name for adding a product.
     */
    @GetMapping("/add")
    public String showAddProductPage(Model model) {
        model.addAttribute("product", new ProductModel()); // Bind empty ProductModel for form
        return "addProduct"; // Render add-product.html
    }

    /**
     * Handles the form submission to add a new product.
     * 
     * @param product The ProductModel object containing the product details.
     * @param redirectAttributes Attributes to pass messages to the view after submission.
     * @return A redirect to the product catalog page, showing success or error messages.
     */
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductModel product, RedirectAttributes redirectAttributes) {
        if (product.getStock() == null || product.getStock() < 0) {
            redirectAttributes.addFlashAttribute("error", "Stock must be greater than or equal to 0.");
            return "redirect:/products/add";
        }
        productDetailFacade.addProduct(product); // Ensure addProduct method saves the product.
        redirectAttributes.addFlashAttribute("success", "Product added successfully!");
        return "redirect:/products/catalog";
    }

    /**
     * Displays the catalog page with a list of products. Supports search and color filters.
     * 
     * @param edit Flag to indicate whether to show in edit mode.
     * @param color The color filter for products.
     * @param search The search query for filtering products by name.
     * @param model The model to pass product data and filter info to the view.
     * @return The name of the catalog view to render.
     */
    @GetMapping("/catalog")
    public String getCatalogPage(@RequestParam(defaultValue = "false") boolean edit,
                                  @RequestParam(required = false) String color,
                                  @RequestParam(required = false) String search, Model model) {
        List<ProductModel> products;

        if (search != null && !search.isEmpty()) {
            products = productDetailFacade.getProductsByName(search); // Fetch products based on the search query
        } else if (color != null && !color.isEmpty()) {
            products = productDetailFacade.getProductsByColor(color); // Fetch filtered products by color
        } else {
            products = productDetailFacade.getAllProductsWithStock(); // Fetch all products if no filter
        }

        model.addAttribute("products", products);
        model.addAttribute("editMode", edit);
        model.addAttribute("search", search);  // Add search term to the model for rendering
        return "catalog"; // Render the catalog view
    }

    /**
     * Displays a paginated list of products in the catalog with optional search.
     * 
     * @param page The page number for pagination.
     * @param search The search term for filtering products by name.
     * @param pageable The Pageable object containing pagination information.
     * @param model The model to pass paginated data to the view.
     * @return The name of the view to render for paginated products.
     */
    @GetMapping("/catalog-paginated")
    public String getPaginatedCatalog(
            @RequestParam(defaultValue = "0") int page, // Default page = 0 (first page)
            @RequestParam(required = false) String search, // Optional search query
            @PageableDefault(size = 3) Pageable pageable, // Default page size of 3
            Model model) {

        // Ensure the Pageable object is constructed with the correct page size
        pageable = PageRequest.of(page, pageable.getPageSize());

        // Get the page of products, filtered by the search term
        Page<ProductModel> productPage;
        if (search != null && !search.isEmpty()) {
            productPage = productService.searchProducts(search, pageable);
        } else {
            productPage = productService.getPaginatedProducts(pageable);
        }

        // Add attributes to the model
        model.addAttribute("productPage", productPage); // Pass entire Page object
        model.addAttribute("search", search); // Pass search query for the search form
        model.addAttribute("currentPage", productPage); // Pass the current page object to the template

        return "catalogPaginated"; // Name of your Thymeleaf template
    }

    /**
     * REST API Endpoint to get all products with stock in JSON format.
     * 
     * @return A ResponseEntity containing the list of products with stock.
     */
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProductsWithStock() {
        List<ProductModel> products = productDetailFacade.getAllProductsWithStock(); // Use facade to get products with stock
        return ResponseEntity.ok(products);
    }

    /**
     * Displays the product detail page with detailed information and stock.
     * 
     * @param id The ID of the product to view.
     * @param model The model to pass the product details to the view.
     * @return The view name for displaying product details.
     */
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

    /**
     * REST API Endpoint to update a product.
     * 
     * @param updatedProduct The product object containing updated details.
     * @return A ResponseEntity containing the updated product or a 404 error.
     */
    @PutMapping("/update")
    public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel updatedProduct) {
        ProductModel product = productDetailFacade.updateProduct(updatedProduct); // Delegate to facade
        if (product != null) {
            return ResponseEntity.ok(product); // Return updated product
        } else {
            return ResponseEntity.status(404).body(null); // Product not found
        }
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete.
     * @return A redirect to the catalog page after deletion.
     */
    @GetMapping("/product-delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productDetailFacade.deleteProductById(id); // Delegate to facade
        return "redirect:/products/catalog?edit=true"; // Redirect to catalog page in edit mode
    }

    /**
     * REST API Endpoint to filter products by color.
     * 
     * @param color The color of products to filter by.
     * @return A ResponseEntity containing the list of filtered products.
     */
    @GetMapping("/filter/color/{color}")
    public ResponseEntity<List<ProductModel>> getProductsByColor(@PathVariable String color) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByColor(color); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }

    /**
     * REST API Endpoint to filter products by category.
     * 
     * @param category The category of products to filter by.
     * @return A ResponseEntity containing the list of filtered products.
     */
    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByCategory(category); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }

    /**
     * REST API Endpoint to filter products by name.
     * 
     * @param name The name of the products to filter by.
     * @return A ResponseEntity containing the list of filtered products.
     */
    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<ProductModel>> getProductsByName(@PathVariable String name) {
        List<ProductModel> filteredProducts = productDetailFacade.getProductsByName(name); // Delegate to facade
        return ResponseEntity.ok(filteredProducts);
    }
}
