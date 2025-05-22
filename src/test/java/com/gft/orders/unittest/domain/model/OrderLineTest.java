package com.gft.orders.unittest.domain.model;

import com.gft.orders.domain.model.valueObject.OrderLine;
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

        UUID uuid = UUID.randomUUID();
        orderLine.setProduct(uuid);

        assertEquals(uuid, orderLine.getProduct());
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
    void getterProductPrice_Test() {

        BigDecimal price = BigDecimal.valueOf(1.0);

        orderLine.setProductPrice(price);

        assertEquals(price, orderLine.getProductPrice());
    }

    @Test
    void setterLinePrice_Test() {

        orderLine.setLinePrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderLine.getLinePrice());
    }

    @Test
    void equals_Test() {

        UUID uuid = UUID.randomUUID();

        OrderLine orderLine1 = new OrderLine();
        orderLine1.setProduct(uuid);
        orderLine1.setQuantity(1);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(uuid);
        orderLine2.setQuantity(1);

        assertEquals(orderLine1, orderLine2);
    }

    @Test
    void notEquals_Test() {

        UUID uuid = UUID.randomUUID();

        OrderLine orderLine1 = new OrderLine();
        orderLine1.setProduct(uuid);
        orderLine1.setQuantity(1);
        orderLine1.setProductPrice(BigDecimal.valueOf(1.0));

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setProduct(uuid);
        orderLine2.setQuantity(1);
        orderLine2.setProductPrice(BigDecimal.valueOf(2.0));

        assertNotEquals(orderLine1, orderLine2);
    }

    /***********PRIVATE METHODS***********/
    private void initData() {

        orderLine = Instancio.create(OrderLine.class);

    }
}