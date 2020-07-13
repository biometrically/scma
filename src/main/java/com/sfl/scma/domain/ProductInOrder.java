package com.sfl.scma.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.scma.enums.ProductInOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductInOrder {

    @NotNull
    private UUID id;

    @NotNull
    private Long productId;

    @NotNull
    private Integer count;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProductInOrderStatus status = ProductInOrderStatus.ACTIVE;

    public ProductInOrder(@NotNull UUID id, @NotNull Long productId, @NotNull Integer count) {
        this.id = id;
        this.productId = productId;
        this.count = count;
    }
}