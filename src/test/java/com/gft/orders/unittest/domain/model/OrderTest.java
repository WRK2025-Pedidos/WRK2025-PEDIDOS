package com.gft.orders.unittest.domain.model;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLine;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        initData();
    }

    @Test
    void constructor_Test() {

        Order orderConstructor = Order.builder().build();

        assertNull(orderConstructor.getId());
        assertNull(orderConstructor.getCartId());
        assertNull(orderConstructor.getTotalPrice());
        assertNull(orderConstructor.getCountryTax());
        assertNull(orderConstructor.getPaymentMethod());
        assertNull(orderConstructor.getCreationDate());
        assertNull(orderConstructor.getOrderLines());
        assertNull(orderConstructor.getOffers());
    }

    @Test
    void setAndGetId_Test() {

        UUID id = UUID.randomUUID();

        order.setId(id);

        assertEquals(id, order.getId());
    }

    @Test
    void setAndGetCartId_Test() {

        UUID cartId = UUID.randomUUID();

        order.setCartId(cartId);

        assertEquals(cartId, order.getCartId());
    }

    @Test
    void setAndGetTotalPrice_Test() {

        BigDecimal totalPrice = new BigDecimal("1.00");

        order.setTotalPrice(totalPrice);

        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    void setAndGetCountryTax_Test() {

        Double countryTax = 1.0;

        order.setCountryTax(countryTax);

        assertEquals(countryTax, order.getCountryTax());
    }

    @Test
    void setAndGetPaymentMethod_Test() {

        Double paymentMethod = 1.0;

        order.setPaymentMethod(paymentMethod);

        assertEquals(paymentMethod, order.getPaymentMethod());
    }

    @Test
    void setAndGetCreationDate_Test() {

        LocalDateTime creationDate = LocalDateTime.now();

        order.setCreationDate(creationDate);

        assertEquals(creationDate, order.getCreationDate());
    }

    @Test
    void setAndGetOrderLines_Test() {

        List<OrderLine> orderLines = Instancio.createList(OrderLine.class);

        order.setOrderLines(orderLines);

        assertEquals(orderLines, order.getOrderLines());
    }

    @Test
    void setAndGetOffers_Test() {

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

        order.setId(id);
        order.setCartId(cartId);

        Order order2 = Order.builder()
                .id(id)
                .cartId(cartId)
                .build();

        assertEquals(order, order2);
    }

    /***********PRIVATE METHODS***********/
    private void initData() {

        order = Instancio.create(Order.class);

    }
}
