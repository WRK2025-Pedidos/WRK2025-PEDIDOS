package com.gft.orders.unittest.messaging.consumer;

import com.gft.orders.business.model.DTO.ProductLowStock;
import com.gft.orders.messaging.consumer.StockNotificationChange;
import com.gft.orders.messaging.producer.StockNotificationAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class StockNotificationChangeTest {

    private StockNotificationAdmin stockNotificationAdmin;
    private StockNotificationChange stockNoticationChange;

    @BeforeEach
    void setUp() {
        stockNotificationAdmin = mock(StockNotificationAdmin.class);
        stockNoticationChange = new StockNotificationChange(stockNotificationAdmin);
    }

    @Test
    void testReceiveNotification() {
        ProductLowStock productLowStock = new ProductLowStock(1L, 3);
        stockNoticationChange.receiveNotification(productLowStock);

        verify(stockNotificationAdmin, times(1)).publishUserDisabledEvent(productLowStock);
    }
}
