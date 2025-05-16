package com.gft.orders.domain.repository;

import com.gft.orders.infraestructure.persistence.OrderOfferEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOfferRepository extends JpaRepository<OrderOfferEntity, OrderOfferId> {
}
