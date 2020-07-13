package com.sfl.scma.repository;

import com.sfl.scma.entity.OrderEntity;
import com.sfl.scma.entity.TableEntity;
import com.sfl.scma.entity.UserEntity;
import com.sfl.scma.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findOrdersByTableAndStatus(TableEntity tableEntity, OrderStatus status);

    List<OrderEntity> findByWaiter(UserEntity userEntity);
}
