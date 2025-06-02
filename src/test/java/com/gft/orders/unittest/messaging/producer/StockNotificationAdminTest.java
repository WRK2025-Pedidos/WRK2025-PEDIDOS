package com.gft.orders.unittest.messaging.producer;

import com.gft.orders.business.model.DTO.ProductLowStock;
import com.gft.orders.messaging.producer.StockNotificationAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockNotificationAdminTest {

    private RabbitTemplate rabbitTemplate;
    private StockNotificationAdmin stockNotificationAdmin;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        stockNotificationAdmin = new StockNotificationAdmin(rabbitTemplate);
    }

    @Test
    void publishUserDisabledEvent_shouldSendMessage_whenProductLowStockIsNotNull() {
        ProductLowStock productLowStock = new ProductLowStock(1L, 10);

        stockNotificationAdmin.publishUserDisabledEvent(productLowStock);

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ProductLowStock> messageCaptor = ArgumentCaptor.forClass(ProductLowStock.class);

        verify(rabbitTemplate, times(1))
                .convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), messageCaptor.capture());

        assertEquals("orders", exchangeCaptor.getValue());
        assertEquals("stock.low", routingKeyCaptor.getValue());
        assertEquals(productLowStock, messageCaptor.getValue());
    }

    @Test
    void publishUserDisabledEvent_shouldNotSendMessage_whenProductLowStockIsNull() {
        stockNotificationAdmin.publishUserDisabledEvent(null);
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
    }
}
