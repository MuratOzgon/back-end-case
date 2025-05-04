package org.murat.backendcase.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.murat.backendcase.entity.Asset;
import org.murat.backendcase.entity.Order;
import org.murat.backendcase.entity.Side;
import org.murat.backendcase.entity.Status;
import org.murat.backendcase.entity.User;
import org.murat.backendcase.entity.UserRole;
import org.murat.backendcase.exception.CustomerNotFoundException;
import org.murat.backendcase.exceptions.InvalidUserRoleException;
import org.murat.backendcase.repository.AssetRepository;
import org.murat.backendcase.repository.OrderRepository;
import org.murat.backendcase.repository.UserRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final AssetRepository assetRepository;

	public OrderService(OrderRepository orderRepository,
						UserRepository userRepository,
						AssetRepository assetRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.assetRepository = assetRepository;
	}

	public Order createOrder(Long customerId,
							 String assetName,
							 Side side,
							 BigDecimal size,
							 BigDecimal price) {
		User user = userRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		if (user.getRole() != UserRole.CUSTOMER) {
			throw new InvalidUserRoleException("Orders can only be created for customers.");
		}

		Asset assetTRY = assetRepository.findByCustomerIdAndAssetName(user.getId(), "TRY")
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "TRY asset not found for customer"));

		BigDecimal totalCost = size.multiply(price);

		if (side == Side.BUY) {
			if (assetTRY.getUsableSize().compareTo(totalCost) < 0) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Insufficient TRY balance to place the order.");
			}
			assetTRY.setUsableSize(assetTRY.getUsableSize().subtract(totalCost));
			assetRepository.save(assetTRY);
		} else {
			Asset assetToSell = assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
					.orElseThrow(() -> new ResponseStatusException(
							HttpStatus.BAD_REQUEST, "Asset not found for selling."));

			if (assetToSell.getUsableSize().compareTo(size) < 0) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Insufficient asset amount to place sell order.");
			}
			assetToSell.setUsableSize(assetToSell.getUsableSize().subtract(size));
			assetRepository.save(assetToSell);
		}

		Order order = new Order();
		order.setUser(user);
		order.setAssetName(assetName);
		order.setSide(side);
		order.setSize(size);
		order.setPrice(price);

		return orderRepository.save(order);
	}

	public List<Order> getOrders(Long customerId,
								 LocalDateTime start,
								 LocalDateTime end) {
		return orderRepository.findByUserIdAndCreateDateBetween(customerId, start, end);
	}

	public List<Order> searchOrders(Long customerId,
									String assetName,
									LocalDateTime start,
									LocalDateTime end) {
		return orderRepository.searchOrders(customerId, assetName, start, end);
	}

	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Order not found"));
		if (order.getStatus() == Status.PENDING) {
			order.setStatus(Status.CANCELED);
			orderRepository.save(order);
		} else {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Only pending orders can be canceled");
		}
	}
}
