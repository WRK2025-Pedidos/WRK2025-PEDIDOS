package com.gft.orders.model.valueObject;

import com.gft.orders.domain.model.valueObject.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderLineTest {

    @Test
    void no_arguments_constructor_test() {

        OrderLine orderLine = new OrderLine();

        assertNull(orderLine.getProduct());
        assertEquals(0, orderLine.getQuantity());
        assertNull(orderLine.getLineWeight());
    }

    @Test
    void setter_product_test() {

        OrderLine orderLine = new OrderLine();

        UUID uuid = UUID.randomUUID();
        orderLine.setProduct(uuid);

        assertEquals(uuid, orderLine.getProduct());
    }

    @Test
    void setter_quantity_test() {

        OrderLine orderLine = new OrderLine();

        orderLine.setQuantity(1);

        assertEquals(1, orderLine.getQuantity());
    }

    @Test
    void setter_line_weight_test() {

        OrderLine orderLine = new OrderLine();

        orderLine.setLineWeight(1.0);

        assertEquals(1.0, orderLine.getLineWeight());
    }

    @Test
    void setter_line_price_test() {

        OrderLine orderLine = new OrderLine();

        orderLine.setLinePrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderLine.getLinePrice());
    }
  
    @Test
    void getter_equals_test() {

        UUID uuid = UUID.randomUUID();

        OrderLine orderLine1 = new OrderLine();
        orderLine1.setProduct(uuid);
        orderLine1.setQuantity(1);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(uuid);
        orderLine2.setQuantity(1);

        assertEquals(orderLine1, orderLine2);
    }
}
