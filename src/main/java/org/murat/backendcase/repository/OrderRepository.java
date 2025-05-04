package org.murat.backendcase.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.murat.backendcase.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserIdAndCreateDateBetween(Long customerId, LocalDateTime start,
			LocalDateTime end);

	@Query("SELECT o FROM Order o " + "WHERE (:customerId IS NULL OR o.user.id = :customerId) "
			+ "AND (:assetName IS NULL OR o.assetName = :assetName) "
			+ "AND (:start IS NULL OR :end IS NULL OR o.createDate BETWEEN :start AND :end)")
	List<Order> searchOrders(@Param("customerId") Long customerId, @Param("assetName") String assetName,
			@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	List<Order> findByUserId(Long userId);
}