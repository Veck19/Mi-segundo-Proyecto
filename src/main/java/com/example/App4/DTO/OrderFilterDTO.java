package com.example.App4.DTO;

import java.time.LocalDateTime;

import com.example.App4.entity.OrderStatus;

public class OrderFilterDTO {

    private OrderStatus status;
    private String origin;
    private String destination;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    // Getters y setters
}
