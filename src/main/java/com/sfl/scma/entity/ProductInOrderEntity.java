package com.sfl.scma.entity;

import com.sfl.scma.entity.base.BaseEntity;
import com.sfl.scma.enums.ProductInOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scma_product_in_order")
public class ProductInOrderEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "count")
    private Integer count;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductInOrderStatus status;
}
