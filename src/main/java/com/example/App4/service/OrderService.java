package com.example.App4.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.App4.DTO.OrderRequestDTO;
import com.example.App4.DTO.OrderStatusUpdateDTO;
import com.example.App4.DTO.Drivers.Assignament.AssignmentRequestDTO;
import com.example.App4.entity.Order;
import com.example.App4.entity.OrderStatus;

public interface OrderService {

    Order createOrder(OrderRequestDTO orderRequestDto);

    Order getOrderById(UUID id);

    List<Order> getOrdersFiltered(OrderStatus status, String origin, String destination, LocalDateTime startDate, LocalDateTime endDate);

    Order updateOrderStatus(UUID orderId, OrderStatusUpdateDTO orderStatusUpdateDTO);

    Order assignDriverToOrder(UUID orderId, UUID driverId);

	List<Order> getOrdersWithFilters(String status, String origin, String destination, Object object, Object object2);

	void assignDriver(UUID orderId, AssignmentRequestDTO assignmentRequestDTO);

	void uploadFile(UUID orderId, MultipartFile file);

	Order createOrder(Order order);

	Order updateOrderStatus(UUID orderId, OrderStatus newStatus);
}
