package com.gft.orders.domain.repository;

import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOfferRepository extends JpaRepository<OrderOfferJPAEntity, OrderOfferIdJPA> {

}
