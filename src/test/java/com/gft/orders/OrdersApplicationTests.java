package com.gft.orders;

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
