package com.gft.orders.unittest.business.model;

import com.gft.orders.business.model.OrderOffer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class OrderOfferTest {

    @Test
    void testOrderOfferBuilderAndGetters() {
        UUID orderId = UUID.randomUUID();
        Long offerId = 1L;

        OrderOffer orderOffer = OrderOffer.builder()
                .orderId(orderId)
                .offerId(offerId)
                .build();

        assertEquals(orderId, orderOffer.getOrderId());
        assertEquals(offerId, orderOffer.getOfferId());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        UUID orderId = UUID.randomUUID();

        OrderOffer orderOffer = new OrderOffer();
        orderOffer.setOrderId(orderId);
        orderOffer.setOfferId(orderOffer.getOfferId());

        assertEquals(orderId, orderOffer.getOrderId());
    }
}
