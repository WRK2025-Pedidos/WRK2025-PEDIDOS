package com.gft.orders.domain.repository;

import com.gft.orders.domain.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

//    void create(Order order);
//    Optional<Order> findById(UUID id);
//    List<Order> findAll(Order order);
//    void update(Order order);

    void modifyStockProduct(UUID id, UUID productId, int quantity);
    void reviewPromotion(UUID id);

}
