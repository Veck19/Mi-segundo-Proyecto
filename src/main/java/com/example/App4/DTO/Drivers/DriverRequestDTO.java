package com.example.App4.DTO.Drivers;

import jakarta.validation.constraints.NotBlank;

public class DriverRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String licenseNumber;

    private boolean active = true; // Valor por defecto

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
