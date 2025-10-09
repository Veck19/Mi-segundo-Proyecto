package com.example.App4.repository;

import com.example.App4.entity.Order;
import com.example.App4.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Buscar por estado
    List<Order> findByStatus(OrderStatus status);

    // Buscar por origen (puedes hacer búsquedas parciales con "Containing")
    List<Order> findByOriginContainingIgnoreCase(String origin);

    // Buscar por destino
    List<Order> findByDestinationContainingIgnoreCase(String destination);

    // Buscar por rango de fechas de creación
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Combinaciones más complejas pueden hacerse con @Query o Specifications si necesitas
}
