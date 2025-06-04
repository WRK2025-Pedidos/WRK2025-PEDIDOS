package com.gft.orders.unittest;

import com.gft.orders.OrdersApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrdersApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        OrdersApplication.main(new String[0]);

        assertTrue(true);
    }

}
