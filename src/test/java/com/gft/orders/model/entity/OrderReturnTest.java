package com.gft.orders.model.entity;

import com.gft.orders.domain.model.entity.OrderReturn;
import com.gft.orders.domain.model.valueObject.OrderLine;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderReturnTest {

    OrderReturn orderReturn;

    @BeforeEach
    void setUp() {
        initData();
    }

    @Test
    void constructor_Test() {

        OrderReturn orderReturnConstructor = OrderReturn.builder().build();

        assertNull(orderReturnConstructor.getId());
        assertNull(orderReturnConstructor.getOrderId());
        assertNull(orderReturnConstructor.getTotalPrice());
        assertNull(orderReturnConstructor.getCreationDate());
        assertNull(orderReturnConstructor.getOrderLines());
    }

    @Test
    void getId_Test() {

        UUID id = UUID.randomUUID();

        orderReturn.setId(id);

        assertEquals(id, orderReturn.getId());
    }

    @Test
    void getOrderId_Test() {

        UUID id = UUID.randomUUID();

        orderReturn.setOrderId(id);

        assertEquals(id, orderReturn.getOrderId());
    }

    @Test
    void getTotalPrice_Test() {

        orderReturn.setTotalPrice(BigDecimal.valueOf(1.0));

        assertEquals(BigDecimal.valueOf(1.0), orderReturn.getTotalPrice());
    }

    @Test
    void getCreationDate_Test() {

        LocalDateTime date = LocalDateTime.now();

        orderReturn.setCreationDate(date);

        assertEquals(date, orderReturn.getCreationDate());
    }

    @Test
    void getOrderLines_Test() {

        List<OrderLine> orderLines = Instancio.createList(OrderLine.class);

        orderReturn.setOrderLines(orderLines);

        assertEquals(orderLines, orderReturn.getOrderLines());
    }

    @Test
    void equals_Test() {

        OrderReturn orderReturn2 = Instancio.create(OrderReturn.class);

        UUID id = UUID.randomUUID();

        orderReturn.setId(id);
        orderReturn2.setId(id);

        assertEquals(orderReturn, orderReturn2);
    }

    /***********PRIVATE METHODS***********/
    private void initData() {

        orderReturn = Instancio.create(OrderReturn.class);

    }

}
