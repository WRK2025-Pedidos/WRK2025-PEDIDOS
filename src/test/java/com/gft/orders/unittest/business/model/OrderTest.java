package com.gft.orders.unittest.business.model;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
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

        Order orderConstructor = new Order();

        assertNull(orderConstructor.getId());
        assertNull(orderConstructor.getCartId());
        assertNull(orderConstructor.getTotalPrice());
        assertNull(orderConstructor.getCountryTax());
        assertNull(orderConstructor.getPaymentMethod());
        assertNull(orderConstructor.getCreationDate());
        assertNull(orderConstructor.getOrderLines());
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
    void equals_Test() {
        UUID id = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();

        order.setId(id);
        order.setCartId(cartId);

        Order order2 = new Order();
        order2.setId(id);
        order2.setCartId(cartId);

        assertEquals(order, order2);
    }

    @Test
    void setAndGetOrderReturn_Test() {

        order.setOrderReturn(true);

        assertTrue(order.getOrderReturn());
    }

    @Test
    void setAndGetParentOrderID_Test() {

        UUID id = UUID.randomUUID();

        order.setParentOrderId(id);

        assertEquals(id, order.getParentOrderId());
    }

    /***********PRIVATE METHODS***********/
    private void initData() {

        order = Instancio.create(Order.class);

    }
}
