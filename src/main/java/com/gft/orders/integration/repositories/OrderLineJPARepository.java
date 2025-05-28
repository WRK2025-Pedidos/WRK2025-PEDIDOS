package com.gft.orders.integration.repositories;

import com.gft.orders.integration.model.OrderLineJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderLineJPARepository extends JpaRepository<OrderLineJPA, UUID> {

    @Query("""
           SELECT oL
             FROM OrderLineJPA oL
            WHERE oL.id = :orderLineId
              AND oL.quantity > :quantity
           """)
    boolean enoughProductQuantity(UUID orderLineId, Integer quantity);
}
