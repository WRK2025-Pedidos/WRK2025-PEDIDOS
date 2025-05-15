package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.OrderReturn;
import org.junit.jupiter.api.Test;

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


}
