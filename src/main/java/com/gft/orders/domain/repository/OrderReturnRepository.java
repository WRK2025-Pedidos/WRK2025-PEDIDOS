package com.gft.orders.domain.repository;

import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderReturnRepository extends JpaRepository<OrderReturnJPAEntity, UUID> {

}
