package com.gft.orders.integrationtest;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderIT {

    @Test
    public void testOrderWithOrderLines_TotalPriceShouldMatchSumOfLinePrices() {
        List<OrderLine> orderLines = createSampleOrderLines();

        BigDecimal expectedTotal = orderLines.stream()
                .map(OrderLine::getLinePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Instancio.of(Order.class)
                .set(Select.field("id"), UUID.randomUUID())
                .set(Select.field("cartId"), UUID.randomUUID())
                .set(Select.field("creationDate"), LocalDateTime.now())
                .set(Select.field("orderLines"), orderLines)
                .set(Select.field("totalPrice"), expectedTotal)
                .create();

        assertEquals(2, order.getOrderLines().size());
        assertEquals(expectedTotal, order.getTotalPrice());
    }

    private List<OrderLine> createSampleOrderLines() {
        OrderLine line1 = Instancio.of(OrderLine.class)
                .set(Select.field("quantity"), 2)
                .set(Select.field("productPrice"), BigDecimal.valueOf(10.00))
                .set(Select.field("linePrice"), BigDecimal.valueOf(20.00))
                .create();

        OrderLine line2 = Instancio.of(OrderLine.class)
                .set(Select.field("quantity"), 3)
                .set(Select.field("productPrice"), BigDecimal.valueOf(15.00))
                .set(Select.field("linePrice"), BigDecimal.valueOf(45.00))
                .create();

        return List.of(line1, line2);
    }
}
