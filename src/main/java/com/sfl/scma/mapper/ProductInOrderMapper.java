package com.sfl.scma.mapper;

import com.sfl.scma.domain.ProductInOrder;
import com.sfl.scma.entity.ProductInOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProductInOrderMapper {
    @Mapping(source = "product.id", target = "productId")
    ProductInOrder toDomain(ProductInOrderEntity productInOrderEntity);

    ProductInOrderEntity toEntity(ProductInOrder productInOrder);
}
