package com.example.App4.DTO;

import com.example.App4.entity.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {

    @NotNull
    private OrderStatus status;
}
