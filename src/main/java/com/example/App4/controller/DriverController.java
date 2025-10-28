package com.example.App4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.App4.DTO.Drivers.DriverRequestDTO;
import com.example.App4.DTO.Drivers.DriverResponseDTO;
import com.example.App4.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Driver")
@Tag(name = "Driver API", description = "Operaciones relacionadas con conductores")
public class DriverController {

	@Autowired
	private DriverService driverService;

	@PostMapping("/Create")
	@Operation(summary = "Crear un nuevo conductor")
	public String createDriver(@Valid @RequestBody DriverRequestDTO driverRequestDTO) {
		driverService.createDriver(driverRequestDTO);
		return "Conductor creado: " + driverRequestDTO.getName();
	}

	@GetMapping("/Active")
	@Operation(summary = "Listar conductores activos")
	public ResponseEntity<List<DriverResponseDTO>> getActiveDrivers() {
		List<DriverResponseDTO> activeDrivers = driverService.getActiveDrivers();
		return ResponseEntity.ok(activeDrivers);
	}
}
