package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLines;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
   @Test
    void constructor_Test() {

        Order order = new Order();

        assertNull(order.getId());
        assertNull(order.getCartId());
        assertNull(order.getTotalPrice());
        assertNull(order.getCountryTax());
        assertNull(order.getPaymentMethod());
        assertNull(order.getCreationDate());
        assertNull(order.getOrderLines());
        assertNull(order.getOffers());
    }

    @Test
    void setAndGetId_Test() {
        Order order = new Order();
        UUID id = UUID.randomUUID();

        order.setId(id);

        assertEquals(id, order.getId());
    }

    @Test
    void setAndGetCartId_Test() {
        Order order = new Order();
        UUID cartId = UUID.randomUUID();

        order.setCartId(cartId);

        assertEquals(cartId, order.getCartId());
    }

    @Test
    void setAndGetTotalPrice_Test() {
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal("1.00");

        order.setTotalPrice(totalPrice);

        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    void setAndGetCountryTax_Test() {
        Order order = new Order();
        Double countryTax = 1.0;

        order.setCountryTax(countryTax);

        assertEquals(countryTax, order.getCountryTax());
    }

    @Test
    void setAndGetPaymentMethod_Test() {
        Order order = new Order();
        Double paymentMethod = 1.0;

        order.setPaymentMethod(paymentMethod);

        assertEquals(paymentMethod, order.getPaymentMethod());
    }

    @Test
    void setAndGetCreationDate_Test() {
        Order order = new Order();
        LocalDate creationDate = LocalDate.now();

        order.setCreationDate(creationDate);

        assertEquals(creationDate, order.getCreationDate());
    }

    @Test
    void setAndGetOrderLines_Test() {
        Order order = new Order();
        OrderLines line = new OrderLines();
        List<OrderLines> orderLines = List.of(line);

        order.setOrderLines(orderLines);

        assertEquals(orderLines, order.getOrderLines());
    }

    @Test
    void setAndGetOffers_Test() {
        Order order = new Order();
        UUID offer1 = UUID.randomUUID();
        UUID offer2 = UUID.randomUUID();
        List<UUID> offers = List.of(offer1, offer2);

        order.setOffers(offers);

        assertEquals(offers, order.getOffers());
    }


    @Test
    void equals_Test() {
        UUID id = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();

        Order order1 = new Order();
        order1.setId(id);
        order1.setCartId(cartId);

        Order order2 = new Order();
        order2.setId(id);
        order2.setCartId(cartId);

        order2.setOffers(List.of());

        assertEquals(order1, order2);
    }
}

