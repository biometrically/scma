package com.sfl.scma.api;

import com.sfl.scma.domain.Order;
import com.sfl.scma.domain.OrderRequest;
import com.sfl.scma.domain.ProductInOrder;
import com.sfl.scma.enums.OrderStatus;
import com.sfl.scma.enums.ProductInOrderStatus;
import com.sfl.scma.security.jwt.domain.ScmaUserPrincipal;
import com.sfl.scma.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class OrderApi {

    private final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * @return All orders from DB.
     */
    @GetMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_MANAGER)")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * @param order     to persist in database
     * @param principal to check if this order is users order.
     * @return Created order with id
     */
    @PostMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public Order createOrder(@AuthenticationPrincipal ScmaUserPrincipal principal, @RequestBody OrderRequest order) {
        return orderService.createOrder(principal.getId(), order);
    }

    /**
     * @param order     to persist in database
     * @param principal to check if this order is users order.
     * @return Created order with id
     */
    @PostMapping("/products")
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public Order addProductToOrder(@AuthenticationPrincipal ScmaUserPrincipal principal, @RequestBody OrderRequest order) {
        return orderService.addProductsToOrder(principal.getId(), order);
    }

    /**
     * @param orderId   which must be edited
     * @param status    new status for edit.
     * @param principal to check if this order is users order.
     * @return updated order object
     */
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public Order editOrderStatus(@AuthenticationPrincipal ScmaUserPrincipal principal,
                                 @PathVariable Long orderId, @RequestParam OrderStatus status) {
        return orderService.editOrderStatus(principal.getId(), orderId, status);
    }

    /**
     * @param productRowId which must be updated
     * @param principal    to check if this order is users order.
     * @param status       new status for edit.
     * @param count        updated count.
     * @return updated productsInOrder object.
     */
    @PatchMapping("/products/{productRowId}")
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public ProductInOrder editProductsInOrder(@AuthenticationPrincipal ScmaUserPrincipal principal,
                                              @PathVariable UUID productRowId,
                                              @RequestParam(required = false) ProductInOrderStatus status,
                                              @RequestParam(required = false) Integer count) {
        return orderService.editProductsInOrder(principal.getId(), productRowId, status, count);
    }
}