package com.gft.orders.application.dto;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderResponseTest {

    @Test
    public void constructor_Test() {
        OrderResponse orderResponse = Instancio.create(OrderResponse.class);
        assertNotNull(orderResponse.cartId());
        assertNotNull(orderResponse.totalPrice());
        assertNotNull(orderResponse.countryTax());
        assertNotNull(orderResponse.paymentMethod());
        assertNotNull(orderResponse.creationDate());
        assertNotNull(orderResponse.orderLines());
        assertNotNull(orderResponse.offers());
    }
}
