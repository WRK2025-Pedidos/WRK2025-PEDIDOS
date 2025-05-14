package com.gft.orders.domainTest;

import com.gft.orders.domain.model.Order;
import com.gft.orders.domain.model.OrderLines;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void constructor_Test() {
        Order order = new Order();
        assertNotNull(order);
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
