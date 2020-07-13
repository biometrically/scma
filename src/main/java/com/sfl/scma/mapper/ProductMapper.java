package com.sfl.scma.mapper;

import com.sfl.scma.domain.Product;
import com.sfl.scma.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductEntity productEntity);

    ProductEntity toEntity(Product product);
}
