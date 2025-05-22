package com.gft.orders.application.infraestructure.persistence;

import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderReturnJPAEntityTest {

    @Test
    public void addReturnLine_Test() {
        OrderReturnJPAEntity orderReturn = new OrderReturnJPAEntity();
        orderReturn.setId(UUID.randomUUID());
        orderReturn.setTotalPrice(BigDecimal.ZERO);
        orderReturn.setCreationDate(LocalDateTime.now());

        OrderLineJPAEntity orderLine = new OrderLineJPAEntity();
        orderLine.setId(UUID.randomUUID());

        orderReturn.addReturnLine(orderLine);

        assertEquals(1, orderReturn.getReturnLines().size());
        assertSame(orderLine, orderReturn.getReturnLines().get(0));
        assertSame(orderReturn, orderLine.getOrderReturn());
    }

}
