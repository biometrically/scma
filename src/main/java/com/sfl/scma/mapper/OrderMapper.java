package com.sfl.scma.mapper;

import com.sfl.scma.domain.Order;
import com.sfl.scma.domain.ProductInOrder;
import com.sfl.scma.domain.Table;
import com.sfl.scma.entity.OrderEntity;
import com.sfl.scma.entity.ProductInOrderEntity;
import com.sfl.scma.entity.TableEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toDomain(OrderEntity orderEntity);

    List<Order> toDomain(List<OrderEntity> orderEntity);

    OrderEntity toEntity(Order order);

    default List<ProductInOrder> productsInOrderEntitiesToDomain(List<ProductInOrderEntity> productInOrderEntities) {
        return productInOrderEntities.stream()
                .map(entity -> {
                    ProductInOrder productInOrder = new ProductInOrder();
                    productInOrder.setId(entity.getId());
                    productInOrder.setCount(entity.getCount());
                    productInOrder.setProductId(entity.getProduct().getId());
                    productInOrder.setStatus(entity.getStatus());
                    return productInOrder;
                })
                .collect(Collectors.toList());
    }

    default Table tableEntityToDomain(TableEntity entity) {
        Table table = new Table();
        table.setId(entity.getId());
        table.setWaiterId(entity.getWaiter().getId());
        return table;
    }
}
