package com.sfl.scma.repository;

import com.sfl.scma.entity.ProductInOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrderEntity, UUID> {
}
