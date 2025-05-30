package com.gft.orders.integration.repositories;

import com.gft.orders.integration.model.OrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderJPARepository extends JpaRepository<OrderJPA, UUID> {

    @Query("""
            SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END
            FROM OrderJPA o
            WHERE o.id = :orderId
            AND o.creationDate < :dateLimit
           """)
    boolean idDateBeforeThirtyDays(UUID orderId, LocalDateTime dateLimit);
}
