package com.example.App4.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.App4.DTO.Drivers.DriverRequestDTO;
import com.example.App4.DTO.Drivers.DriverResponseDTO;
import com.example.App4.entity.Driver;
import com.example.App4.repository.DriverRepository;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public DriverResponseDTO createDriver(DriverRequestDTO driverRequestDTO) {
        Driver driver = new Driver();
        driver.setId(UUID.randomUUID());
        driver.setName(driverRequestDTO.getName());
        driver.setLicenseNumber(driverRequestDTO.getLicenseNumber());
        driver.setActive(driverRequestDTO.isActive());

        Driver savedDriver = driverRepository.save(driver);

        DriverResponseDTO response = new DriverResponseDTO();
        response.setId(savedDriver.getId());
        response.setName(savedDriver.getName());
        response.setLicenseNumber(savedDriver.getLicenseNumber());
        response.setActive(savedDriver.isActive());

        return response;
    }

    public List<DriverResponseDTO> getActiveDrivers() {
        List<Driver> activeDrivers = driverRepository.findByActiveTrue();

        return activeDrivers.stream().map(driver -> {
            DriverResponseDTO dto = new DriverResponseDTO();
            dto.setId(driver.getId());
            dto.setName(driver.getName());
            dto.setLicenseNumber(driver.getLicenseNumber());
            dto.setActive(driver.isActive());
            return dto;
        }).collect(Collectors.toList());
    }
}
