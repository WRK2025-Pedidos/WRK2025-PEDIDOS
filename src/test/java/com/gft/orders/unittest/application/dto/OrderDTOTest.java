package com.gft.orders.unittest.application.dto;

import com.gft.orders.application.dto.OrderDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDTOTest {

    @Test
    public void constructor_Test() {
        OrderDTO orderDTO = Instancio.create(OrderDTO.class);
        assertNotNull(orderDTO.cartId());
        assertNotNull(orderDTO.totalPrice());
        assertNotNull(orderDTO.countryTax());
        assertNotNull(orderDTO.paymentMethod());
        assertNotNull(orderDTO.creationDate());
        assertNotNull(orderDTO.orderLines());
        assertNotNull(orderDTO.offers());
    }
}