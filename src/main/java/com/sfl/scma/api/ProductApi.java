package com.sfl.scma.api;

import com.sfl.scma.domain.Product;
import com.sfl.scma.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    /**
     * @param product to persist in database
     * @return Created product with id.
     */
    @PostMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_MANAGER)")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * @return All products from DB.
     */
    @GetMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}