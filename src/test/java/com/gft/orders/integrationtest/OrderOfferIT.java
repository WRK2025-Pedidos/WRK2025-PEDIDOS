package com.gft.orders.integrationtest;

import com.gft.orders.business.model.OrderOffer;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderOfferIT {

    private final List<OrderOffer> orderOffers = new ArrayList<>();

    @Test
    void testOrderOfferIntegration() {
        UUID orderId = UUID.randomUUID();

        OrderOffer orderOffer = new OrderOffer();
        orderOffer.setOrderId(orderId);
        orderOffer.setOfferId(orderOffer.getOfferId());

        orderOffers.add(orderOffer);

        OrderOffer orderOffer1 = orderOffers.stream()
                .filter(o -> o.getOrderId().equals(orderOffer.getOrderId()))
                .findFirst()
                .orElse(null);

        assertNotNull(orderOffer1);
        assertEquals(orderId, orderOffer1.getOrderId());
        assertEquals(orderOffer.getOfferId(), orderOffer1.getOfferId());
    }
}
