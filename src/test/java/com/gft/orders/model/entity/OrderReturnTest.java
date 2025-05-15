package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.OrderReturn;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Test
    void getOrderIdTest() {

        OrderReturn orderReturn = new OrderReturn();
        UUID id = UUID.randomUUID();

        orderReturn.setOrderId(id);

        assertEquals(id, orderReturn.getOrderId());
    }

    @Test
    void getTotalPriceTest() {

        OrderReturn orderReturn = new OrderReturn();

        orderReturn.setTotalPrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderReturn.getTotalPrice());
    }

    @Test
    void getCountryTaxTest() {

        OrderReturn orderReturn = new OrderReturn();

        orderReturn.setCountryTax(2.0);

        assertEquals(2.0, orderReturn.getCountryTax());
    }

    @Test
    void getPaymentMethodTest() {

        OrderReturn orderReturn = new OrderReturn();

        orderReturn.setPaymentMethod(0.3);

        assertEquals(0.3, orderReturn.getPaymentMethod());
    }

    @Test
    void getCreationDateTest() {

        OrderReturn orderReturn = new OrderReturn();
        LocalDate date = LocalDate.now();

        orderReturn.setCreationDate(date);

        assertEquals(date, orderReturn.getCreationDate());
    }
}
