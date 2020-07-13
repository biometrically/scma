package com.sfl.scma.domain;

import com.sfl.scma.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Order {
    private Long id;
    private Table table;
    private List<ProductInOrder> productInOrders;
    private OrderStatus status;
}