package com.example.App4.DTO.Drivers;

import java.util.UUID;

public class DriverBasicDTO {

    private UUID id;
    private String name;

    // Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
