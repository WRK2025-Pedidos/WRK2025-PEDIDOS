package com.gft.orders.unittest.business.model;

import com.gft.orders.business.model.OrderLine;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLineTest {

    OrderLine orderLine;

    @BeforeEach
    void setUp() {
        initData();
    }
    @Test
    void noArgumentsConstructor_Test() {

        OrderLine orderLineConstructor = new OrderLine();

        assertNull(orderLineConstructor.getProduct());
        assertEquals(0, orderLineConstructor.getQuantity());
        assertNull(orderLineConstructor.getLineWeight());
    }

    @Test
    void setterProduct_Test() {

        Long id = 1L;
        orderLine.setProduct(id);

        assertEquals(id, orderLine.getProduct());
    }

    @Test
    void setterQuantity_Test() {

        orderLine.setQuantity(1);

        assertEquals(1, orderLine.getQuantity());
    }

    @Test
    void setterLineWeight_Test() {

        orderLine.setLineWeight(1.0);

        assertEquals(1.0, orderLine.getLineWeight());
    }

    @Test
    void setterLinePrice_Test() {

        orderLine.setLinePrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderLine.getLinePrice());
    }

    @Test
    void equals_Test() {

        Long id = 1L;

        OrderLine orderLine1 = new OrderLine();
        orderLine1.setProduct(id);
        orderLine1.setQuantity(1);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(id);
        orderLine2.setQuantity(1);

        assertEquals(orderLine1, orderLine2);
    }


    /***********PRIVATE METHODS***********/
    private void initData() {

        orderLine = Instancio.create(OrderLine.class);

    }
}