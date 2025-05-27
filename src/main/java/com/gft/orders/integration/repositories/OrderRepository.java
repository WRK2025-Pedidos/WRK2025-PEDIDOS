package com.gft.orders.integration.repositories;

import com.gft.orders.integration.model.OrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderJPA, UUID> {

}
