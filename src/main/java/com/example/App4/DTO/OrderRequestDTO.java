package com.example.App4.DTO;

import jakarta.validation.constraints.NotBlank;

public class OrderRequestDTO {

    @NotBlank(message = "El origen no puede estar vacío")
    private String origin;

    @NotBlank(message = "El destino no puede estar vacío")
    private String destination;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "OrderRequestDTO{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
