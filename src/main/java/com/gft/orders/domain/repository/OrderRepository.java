package com.gft.orders.domain.repository;

import com.gft.orders.infraestructure.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

}
