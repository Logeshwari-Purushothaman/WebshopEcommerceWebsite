package com.example.webshop;
import com.example.webshop.ProductModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/saas")
public class SaaSCatalogController {

    private final ProductService productService;

    @Autowired
    public SaaSCatalogController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/catalog")
    public List<ProductModel> getCatalog() {
        return productService.getAllProducts();
    }
}

