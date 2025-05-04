package org.murat.backendcase.controller;

import org.murat.backendcase.dto.OrderRequest;
import org.murat.backendcase.entity.Order;
import org.murat.backendcase.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
		Order order = orderService.createOrder(
				request.getCustomerId(),
				request.getAssetName(),
				request.getSide(),
				request.getSize(),
				request.getPrice()
		);
		return ResponseEntity.ok(order);
	}

	@GetMapping
	public ResponseEntity<List<Order>> listOrders(
			@RequestParam Long customerId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
	) {
		List<Order> orders = orderService.getOrders(customerId, start, end);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Order>> searchOrders(
			@RequestParam(required = false) Long customerId,
			@RequestParam(required = false) String assetName,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
	) {
		List<Order> orders = orderService.searchOrders(customerId, assetName, start, end);
		return ResponseEntity.ok(orders);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
		orderService.cancelOrder(orderId);
		return ResponseEntity.noContent().build();
	}
}