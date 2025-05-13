package com.gft.pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PedidosApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        PedidosApplication.main(new String[0]);

        assertTrue(true);
    }
}
