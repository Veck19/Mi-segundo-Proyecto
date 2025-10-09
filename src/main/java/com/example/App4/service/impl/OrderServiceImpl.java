package com.example.App4.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.App4.DTO.OrderRequestDTO;
import com.example.App4.DTO.OrderStatusUpdateDTO;
import com.example.App4.DTO.Drivers.Assignament.AssignmentRequestDTO;
import com.example.App4.entity.Driver;
import com.example.App4.entity.Order;
import com.example.App4.entity.OrderStatus;
import com.example.App4.repository.DriverRepository;
import com.example.App4.repository.OrderRepository;
import com.example.App4.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;

    public OrderServiceImpl(OrderRepository orderRepository, DriverRepository driverRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Order createOrder(OrderRequestDTO orderRequestDto) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatus.CREATED);
        order.setOrigin(orderRequestDto.getOrigin());
        order.setDestination(orderRequestDto.getDestination());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + id));
    }

    @Override
    public List<Order> getOrdersFiltered(OrderStatus status, String origin, String destination,
                                         LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findAll().stream()
                .filter(order -> status == null || order.getStatus().equals(status))
                .filter(order -> origin == null || origin.isBlank() || order.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                .filter(order -> destination == null || destination.isBlank() || order.getDestination().toLowerCase().contains(destination.toLowerCase()))
                .filter(order -> startDate == null || endDate == null || (
                        !order.getCreatedAt().isBefore(startDate) && !order.getCreatedAt().isAfter(endDate)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Order updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);

        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            throw new IllegalStateException("Transición de estado no válida: " + order.getStatus() + " → " + newStatus);
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    private boolean isValidStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == newStatus) return true;

        switch (currentStatus) {
            case CREATED:
                return newStatus == OrderStatus.IN_TRANSIT || newStatus == OrderStatus.CANCELLED;
            case IN_TRANSIT:
                return newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED;
            case DELIVERED:
                return false; // no se permite cambio posterior
            case CANCELLED:
                return false; // no se permite cambio posterior
            default:
                return false;
        }
    }

    @Override
    @Transactional
    public Order assignDriverToOrder(UUID orderId, UUID driverId) {
        Order order = getOrderById(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new IllegalStateException("Solo se puede asignar un conductor a una orden en estado CREATED");
        }

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Conductor no encontrado con ID: " + driverId));

        if (!driver.isActive()) {
            throw new IllegalStateException("No se puede asignar un conductor inactivo");
        }

        order.setDriver(driver);
        order.setUpdatedAt(LocalDateTime.now());

        
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersWithFilters(String status, String origin, String destination, Object obj1, Object obj2) {
        OrderStatus orderStatus = null;
        if (status != null) {
            try {
                orderStatus = OrderStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Estado inválido: " + status);
            }
        }
        return getOrdersFiltered(orderStatus, origin, destination, null, null);
    }

    @Override
    public void assignDriver(UUID orderId, AssignmentRequestDTO assignmentRequestDTO) {
        assignDriverToOrder(orderId, assignmentRequestDTO.getDriverId());
    }

    @Override
    public void uploadFile(UUID orderId, MultipartFile file) {
        validateFile(file);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + orderId));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Error al guardar el archivo");
        }

        order.setAttachedFile(filename);
        orderRepository.save(order);
    }

    private void validateFile(MultipartFile file) {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();

        if (contentType == null || originalFilename == null) {
            throw new IllegalStateException("Archivo inválido");
        }

        boolean isPdf = contentType.equalsIgnoreCase("application/pdf");
        boolean isPng = contentType.equalsIgnoreCase("image/png");
        boolean isJpg = contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/jpg");

        if (!(isPdf || isPng || isJpg)) {
            throw new IllegalStateException("Solo se permiten archivos PDF, PNG o JPG");
        }

        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!(ext.equals("pdf") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))) {
            throw new IllegalStateException("Extensión de archivo no permitida");
        }
    }

    @Override
    public Order updateOrderStatus(UUID orderId, OrderStatusUpdateDTO orderStatusUpdateDTO) {
        Order order = getOrderById(orderId);

        OrderStatus newStatus = orderStatusUpdateDTO.getStatus();
        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            throw new IllegalStateException("Transición de estado no válida: " + order.getStatus() + " → " + newStatus);
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(Order order) {
        // Este método puede ser útil si quieres guardar directamente una entidad Order
        if(order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        if(order.getStatus() == null) {
            order.setStatus(OrderStatus.CREATED);
        }
        if(order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}
