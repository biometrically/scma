package com.sfl.scma.domain;

import com.sfl.scma.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditOrderStatusRequest {

    private OrderStatus status;
}
