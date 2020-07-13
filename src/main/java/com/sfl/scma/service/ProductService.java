package com.sfl.scma.service;

import com.sfl.scma.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product createProduct(Product product);
}
