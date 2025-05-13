package com.gft.pedidos.domainTest;

import com.gft.pedidos.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class OrderTest {
    @Test
    void constructor_Test() {

        Order order = new Order();

        assertNull(order.getId());
        assertNull(order.getIdCart());
        assertNull(order.getCreationDate());
        assertNull(order.getOrderLines());
        assertNull(order.getOffers());

    }
//
//    @Test
//    void getterId_Test() {
//    }
//
//    @Test
//    void getterIdCart_Test() {
//    }
//
//    @Test
//    void getterCreationDate_Test() {
//    }
//
//    @Test
//    void getterOrderLines_Test() {
//    }
//
//    @Test
//    void getterOffers_Test() {
//    }
//
//    @Test
//    void setterId_Test() {
//    }
//
//    @Test
//    void setterIdCart_Test() {
//    }
//
//    @Test
//    void setterCreationDate_Test() {
//    }
//
//    @Test
//    void setterOrderLines_Test() {
//    }
//
//    @Test
//    void setterOffers_Test() {
//    }
//
//    @Test
//    void equals_Test() {
//
//    }

}
