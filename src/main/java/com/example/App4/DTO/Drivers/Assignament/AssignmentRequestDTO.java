package com.example.App4.DTO.Drivers.Assignament;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentRequestDTO {

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID driverId;

   //private MultipartFile document; // PDF
    //private MultipartFile image;    // PNG o JPG
}
