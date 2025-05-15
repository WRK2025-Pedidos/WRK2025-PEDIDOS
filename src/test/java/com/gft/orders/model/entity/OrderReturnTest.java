package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.OrderReturn;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderReturnTest {

    @Test
    void constructorTest() {

        OrderReturn orderReturn = new OrderReturn();

        assertNull(orderReturn.getId());
        assertNull(orderReturn.getOrderId());
        assertNull(orderReturn.getTotalPrice());
        assertNull(orderReturn.getCountryTax());
        assertNull(orderReturn.getPaymentMethod());
        assertNull(orderReturn.getCreationDate());
        assertNull(orderReturn.getOrderLines());
        assertNull(orderReturn.getOffers());
    }

    @Test
    void getIdTest() {

        OrderReturn orderReturn = new OrderReturn();
        UUID id = UUID.randomUUID();

        orderReturn.setId(id);

        assertEquals(id, orderReturn.getId());
    }
}
