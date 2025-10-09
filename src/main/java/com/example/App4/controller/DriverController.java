package com.example.App4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.App4.DTO.Drivers.DriverRequestDTO;
import com.example.App4.DTO.Drivers.DriverResponseDTO;
import com.example.App4.service.DriverService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Crear un nuevo conductor
    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO driverRequestDTO) {
        DriverResponseDTO createdDriver = driverService.createDriver(driverRequestDTO);
        return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
    }

    // Listar conductores activos
    @GetMapping("/active")
    public ResponseEntity<List<DriverResponseDTO>> getActiveDrivers() {
        List<DriverResponseDTO> activeDrivers = driverService.getActiveDrivers();
        return ResponseEntity.ok(activeDrivers);
    }
}
