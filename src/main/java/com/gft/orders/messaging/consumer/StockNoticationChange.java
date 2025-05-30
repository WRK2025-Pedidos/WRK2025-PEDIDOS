package com.gft.orders.messaging.consumer;

import com.gft.orders.messaging.producer.StockNotificationAdmin;
import com.gft.orders.business.model.DTO.ProductLowStock;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockNoticationChange {
    private final StockNotificationAdmin stockNotificationAdmin;

    public StockNoticationChange(StockNotificationAdmin stockNotificationAdmin) {
        this.stockNotificationAdmin = stockNotificationAdmin;
    }

    @RabbitListener(queues = "products.orders.stock.low")
    public void receiveNotification(ProductLowStock productLowStock) {
        stockNotificationAdmin.publishUserDisabledEvent(productLowStock);
    }
}
