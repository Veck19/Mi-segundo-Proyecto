package com.example.App4.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.App4.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    // Buscar todos los conductores activos
    List<Driver> findByActiveTrue();
}
