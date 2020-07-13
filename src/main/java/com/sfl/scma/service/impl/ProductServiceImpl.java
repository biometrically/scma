package com.sfl.scma.service.impl;

import com.sfl.scma.domain.Product;
import com.sfl.scma.entity.ProductEntity;
import com.sfl.scma.mapper.ProductMapper;
import com.sfl.scma.repository.ProductRepository;
import com.sfl.scma.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        productRepository.save(productEntity);
        product.setId(productEntity.getId());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }
}
