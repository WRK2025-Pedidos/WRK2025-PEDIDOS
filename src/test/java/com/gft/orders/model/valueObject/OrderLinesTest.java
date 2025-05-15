package com.gft.orders.model.valueObject;

import com.gft.orders.domain.model.valueObject.OrderLines;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
        assertNull(orderLines.getLineWeight());
    }

    @Test
    void setter_product_test() {

        OrderLines orderLines = new OrderLines();

        UUID uuid = UUID.randomUUID();
        orderLines.setProduct(uuid);

        assertEquals(uuid, orderLines.getProduct());
    }

    @Test
    void setter_quantity_test() {

        OrderLines orderLines = new OrderLines();

        orderLines.setQuantity(1);

        assertEquals(1, orderLines.getQuantity());
    }

    @Test
    void setter_refund_test() {

        OrderLines orderLines = new OrderLines();

        orderLines.setRefund(true);

        assertEquals(true, orderLines.getRefund());
    }

    @Test
    void setter_line_weight_test() {

        OrderLines orderLines = new OrderLines();

        orderLines.setLineWeight(1.0);

        assertEquals(1.0, orderLines.getLineWeight());
    }

    @Test
    void setter_line_price_test() {

        OrderLines orderLines = new OrderLines();

        orderLines.setLinePrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderLines.getLinePrice());
    }
  
    @Test
    void getter_equals_test() {

        UUID uuid = UUID.randomUUID();

        OrderLines orderLines1 = new OrderLines();

        orderLines1.setProduct(uuid);
        orderLines1.setQuantity(1);
        orderLines1.setRefund(true);

        OrderLines orderLines2 = new OrderLines();
        orderLines2.setProduct(uuid);
        orderLines2.setQuantity(1);
        orderLines2.setRefund(true);

        assertEquals(orderLines1, orderLines2);
    }
}
