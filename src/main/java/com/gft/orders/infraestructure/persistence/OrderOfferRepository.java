package com.gft.orders.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOfferRepository extends JpaRepository<OrderOfferEntity, OrderOfferId> {
}
