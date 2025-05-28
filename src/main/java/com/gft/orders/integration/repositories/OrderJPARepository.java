package com.gft.orders.integration.repositories;

import com.gft.orders.integration.model.OrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJPARepository extends JpaRepository<OrderJPA, UUID> {

    @Query("""
           SELECT o
             FROM OrderJPA o
            WHERE o.orderReturn = true
              AND o.parentOrderId = :orderId
           """)
    boolean isFirstReturn(UUID orderId);

    @Query("""
           SELECT o
             FROM OrderJPA o
            WHERE o.id = :orderId
              AND o.creationDate > (CURRENT TIMESTAMP - 30)
           """)
    Object[] dateBeforeThirtyDays(UUID orderId);

    @Query("""
           SELECT o
             FROM OrderJPA o
            WHERE o.orderReturn = true
              AND o.parentOrderId = :orderId
         ORDER BY o.creationDate desc
            LIMIT 1
         """)
    Object[] findLastReturn(UUID orderId);
}
