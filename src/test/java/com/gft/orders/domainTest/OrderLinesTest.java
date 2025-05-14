package com.gft.orders.domainTest;

import com.gft.orders.domain.model.OrderLines;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderLinesTest {

    @Test
    void no_arguments_constructor_test() {

        OrderLines orderLines = new OrderLines();

        assertNull(orderLines.getProduct());
        assertEquals(0, orderLines.getQuantity());
        assertNull(orderLines.getRefund());

    }

    @Test
    void setter_product_test() {

        OrderLines orderLines = new OrderLines();

        UUID uuid = UUID.randomUUID();
        orderLines.setProduct(uuid);

        assertEquals(uuid, orderLines.getProduct());
    }
}
