package com.sfl.scma.service;

import com.sfl.scma.domain.Order;
import com.sfl.scma.domain.OrderRequest;
import com.sfl.scma.domain.ProductInOrder;
import com.sfl.scma.enums.OrderStatus;
import com.sfl.scma.enums.ProductInOrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Long waiterId, OrderRequest order);

    List<Order> getAllOrders();

    Order editOrderStatus(Long waiterId, Long orderId, OrderStatus status);

    ProductInOrder editProductsInOrder(Long waiterId, UUID productsInOrderId, ProductInOrderStatus status, Integer count);

    Order addProductsToOrder(Long id, OrderRequest order);
}
