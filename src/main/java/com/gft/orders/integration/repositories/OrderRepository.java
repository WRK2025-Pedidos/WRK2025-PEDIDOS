package com.gft.orders.domain.repository;

import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderJPAEntity, UUID> {

}
