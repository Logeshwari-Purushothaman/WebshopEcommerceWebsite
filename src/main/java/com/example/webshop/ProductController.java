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

@Controller
@RequestMapping("/products") // Base mapping for all product-related endpoints
public class ProductController {

    private final ProductDetailFacade productDetailFacade; // Use ProductDetailFacade
    private final ShoppingCartService shoppingCartService; // Add ShoppingCartService
    private final ProductService productService;


    // Constructor-based Dependency Injection
    @Autowired
    public ProductController(ProductDetailFacade productDetailFacade, ShoppingCartService shoppingCartService,ProductService productService) {
        this.productDetailFacade = productDetailFacade;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
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
    
    // Add Product Page (Thymeleaf View)
    @GetMapping("/add")
    public String showAddProductPage(Model model) {
        model.addAttribute("product", new ProductModel()); // Bind empty ProductModel for form
        return "addProduct"; // Render add-product.html
    }

    // Add Product (Form Submission via Thymeleaf)
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

    // Catalog Page (List of Products with Inventory)    
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



    // REST API Endpoint to get all products with stock (JSON response)
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProductsWithStock() {
        List<ProductModel> products = productDetailFacade.getAllProductsWithStock(); // Use facade to get products with stock
        return ResponseEntity.ok(products);
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
