package com.gft.orders.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
class OrdersApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        assertTrue(true);
    }

}
