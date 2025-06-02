package com.gft.orders.integrationtest.messaging;

import com.gft.orders.business.model.DTO.ProductLowStock;
import com.gft.orders.messaging.config.RabbitConfig;
import com.gft.orders.messaging.consumer.StockNotificationChange;
import com.gft.orders.messaging.producer.StockNotificationAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(RabbitConfig.class)
@SpringBootTest
public class StockNotificationChangeIT {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StockNotificationChange stockNotificationChange;

    private StockNotificationAdmin stockNotificationAdmin;

    @BeforeEach
    void setup() {
        stockNotificationAdmin = mock(StockNotificationAdmin.class);

        try {
            var field = StockNotificationChange.class.getDeclaredField("stockNotificationAdmin");
            field.setAccessible(true);
            field.set(stockNotificationChange, stockNotificationAdmin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("No se pudo inyectar el mock de StockNotificationAdmin: " + e.getMessage());
        }
    }

    @Test
    void testReceiveNotificationFromQueue() throws InterruptedException {
        ProductLowStock productLowStock = new ProductLowStock(42L, 5);
        rabbitTemplate.convertAndSend("products.orders.stock.low", productLowStock);

        Thread.sleep(1000);

        ArgumentCaptor<ProductLowStock> captor = ArgumentCaptor.forClass(ProductLowStock.class);
        verify(stockNotificationAdmin, times(1)).publishUserDisabledEvent(captor.capture());

        ProductLowStock received = captor.getValue();
        assertEquals(productLowStock.productId(), received.productId());
        assertEquals(productLowStock.stock(), received.stock());
    }
}
