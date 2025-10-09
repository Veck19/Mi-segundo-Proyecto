package com.example.App4.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.App4.DTO.Drivers.DriverBasicDTO;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private UUID id;
    private String status;
    private String origin;
    private String destination;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DriverBasicDTO driver;
}
