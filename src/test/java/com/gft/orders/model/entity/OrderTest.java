package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.OrderEntity;
import com.gft.orders.domain.model.valueObject.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
   @Test
    void constructor_Test() {

        OrderEntity order = new OrderEntity();

        assertNull(order.getId());
        assertNull(order.getCartId());
        assertNull(order.getTotalPrice());
        assertNull(order.getCountryTax());
        assertNull(order.getPaymentMethod());
        assertNull(order.getCreationDate());
        assertNull(order.getOrderLine());
        assertNull(order.getOffers());
    }

    @Test
    void setAndGetId_Test() {
        OrderEntity order = new OrderEntity();
        UUID id = UUID.randomUUID();

        order.setId(id);

        assertEquals(id, order.getId());
    }

    @Test
    void setAndGetCartId_Test() {
        OrderEntity order = new OrderEntity();
        UUID cartId = UUID.randomUUID();

        order.setCartId(cartId);

        assertEquals(cartId, order.getCartId());
    }

    @Test
    void setAndGetTotalPrice_Test() {
        OrderEntity order = new OrderEntity();
        BigDecimal totalPrice = new BigDecimal("1.00");

        order.setTotalPrice(totalPrice);

        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    void setAndGetCountryTax_Test() {
        OrderEntity order = new OrderEntity();
        Double countryTax = 1.0;

        order.setCountryTax(countryTax);

        assertEquals(countryTax, order.getCountryTax());
    }

    @Test
    void setAndGetPaymentMethod_Test() {
        OrderEntity order = new OrderEntity();
        Double paymentMethod = 1.0;

        order.setPaymentMethod(paymentMethod);

        assertEquals(paymentMethod, order.getPaymentMethod());
    }

    @Test
    void setAndGetCreationDate_Test() {
        OrderEntity order = new OrderEntity();
        LocalDateTime creationDate = LocalDateTime.now();

        order.setCreationDate(creationDate);

        assertEquals(creationDate, order.getCreationDate());
    }

    @Test
    void setAndGetOrderLines_Test() {
        OrderEntity order = new OrderEntity();
        OrderLine line = new OrderLine();
        List<OrderLine> orderLine = List.of(line);

        order.setOrderLine(orderLine);

        assertEquals(orderLine, order.getOrderLine());
    }

    @Test
    void setAndGetOffers_Test() {
        OrderEntity order = new OrderEntity();
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

        OrderEntity order1 = new OrderEntity();
        order1.setId(id);
        order1.setCartId(cartId);

        OrderEntity order2 = new OrderEntity();
        order2.setId(id);
        order2.setCartId(cartId);

        order2.setOffers(List.of());

        assertEquals(order1, order2);
    }
}

