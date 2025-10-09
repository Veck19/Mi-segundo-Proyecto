package com.example.App4.OrderController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.App4.DTO.OrderRequestDTO;
import com.example.App4.DTO.OrderStatusUpdateDTO;
import com.example.App4.DTO.Drivers.Assignament.AssignmentRequestDTO;
import com.example.App4.entity.Order;
import com.example.App4.service.OrderService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Order")
@Tag(name = "Order API", description = "Operaciones relacionadas con órdenes")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/Create")
	@Operation(summary = "Crear una orden")
	public String createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDto) {
		orderService.createOrder(orderRequestDto);
		return "Orden creada desde " + orderRequestDto.getOrigin();
	}

	@PatchMapping("/UpdateStatus/{id}")
	@Operation(summary = "Actualizar el estado de una orden")
	public String updateOrderStatus(@PathVariable UUID id,
			@Valid @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) {
		orderService.updateOrderStatus(id, orderStatusUpdateDTO);
		return "Estado actualizado a " + orderStatusUpdateDTO.getStatus();
	}

	@GetMapping("/GetById/{id}")
	@Operation(summary = "Obtener una orden por ID")
	public String getOrderById(@PathVariable UUID id) {
		Order order = orderService.getOrderById(id);
		return "Orden: " + order.getId() + ", Estado: " + order.getStatus();
	}

	@GetMapping("/List")
	@Operation(summary = "Listar órdenes con filtros opcionales")
	public List<Order> listOrders(@RequestParam(required = false) String status,
			@RequestParam(required = false) String origin, @RequestParam(required = false) String destination) {
		return orderService.getOrdersWithFilters(status, origin, destination, null, null);
	}

	@PostMapping("/AssignDriver/{orderId}")
	@Operation(summary = "Asignar un conductor a una orden")
	public String assignDriver(@PathVariable UUID orderId,
			@Valid @RequestBody AssignmentRequestDTO assignmentRequestDTO) {
		orderService.assignDriver(orderId, assignmentRequestDTO);
		return "Conductor asignado a la orden";
	}

	@PostMapping("/UploadFile/{orderId}")
	@Operation(summary = "Subir un archivo relacionado con una orden")
	public ResponseEntity<String> uploadFile(@PathVariable UUID orderId, @RequestParam("file") MultipartFile file) {
		orderService.uploadFile(orderId, file);
		return ResponseEntity.ok("Archivo subido correctamente");
	}
}
