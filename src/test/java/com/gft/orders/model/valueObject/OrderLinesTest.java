package com.gft.orders.model.valueObject;

import com.gft.orders.domain.model.valueObject.OrderLines;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLinesTest {

    OrderLines orderLines;

    @BeforeEach
    void setUp() {
        initData();
    }
    @Test
    void no_arguments_constructor_test() {

        OrderLines orderLinesConstructor = new OrderLines();

        assertNull(orderLinesConstructor.getProduct());
        assertEquals(0, orderLinesConstructor.getQuantity());
        assertNull(orderLinesConstructor.getLineWeight());
    }

    @Test
    void setter_product_test() {

        UUID uuid = UUID.randomUUID();
        orderLines.setProduct(uuid);

        assertEquals(uuid, orderLines.getProduct());
    }

    @Test
    void setter_quantity_test() {

        orderLines.setQuantity(1);

        assertEquals(1, orderLines.getQuantity());
    }

    @Test
    void setter_line_weight_test() {

        orderLines.setLineWeight(1.0);

        assertEquals(1.0, orderLines.getLineWeight());
    }

    @Test
    void getter_productPrice_test() {

        BigDecimal price = BigDecimal.valueOf(1.0);

        orderLines.setProductPrice(price);

        assertEquals(price, orderLines.getProductPrice());
    }

    @Test
    void setter_line_price_test() {

        orderLines.setLinePrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderLines.getLinePrice());
    }
  
    @Test
    void equals_test() {

        UUID uuid = UUID.randomUUID();

        OrderLines orderLines1 = new OrderLines();
        orderLines1.setProduct(uuid);
        orderLines1.setQuantity(1);

        OrderLines orderLines2 = new OrderLines();
        orderLines2.setProduct(uuid);
        orderLines2.setQuantity(1);

        assertEquals(orderLines1, orderLines2);
    }

    @Test
    void not_equals_test() {

        UUID uuid = UUID.randomUUID();

        OrderLines orderLines1 = new OrderLines();
        orderLines1.setProduct(uuid);
        orderLines1.setQuantity(1);
        orderLines1.setProductPrice(BigDecimal.valueOf(1.0));

        OrderLines orderLines2 = new OrderLines();
        orderLines2.setProduct(uuid);
        orderLines2.setQuantity(1);
        orderLines2.setProductPrice(BigDecimal.valueOf(2.0));

        assertNotEquals(orderLines1, orderLines2);
    }

    /***********PRIVATE METHODS***********/
    private void initData() {

        orderLines = Instancio.create(OrderLines.class);

    }
}
