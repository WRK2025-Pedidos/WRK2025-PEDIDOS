package com.gft.orders.messaging.producer;

import com.gft.orders.business.model.DTO.ProductLowStock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockNotificationAdmin {

    private final RabbitTemplate rabbitTemplate;

    public StockNotificationAdmin(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserDisabledEvent(ProductLowStock productLowStock) {
        if (productLowStock == null) {
            return;
        }

        final String exchange = "orders";
        final String routingKey = "stock.low";
        rabbitTemplate.convertAndSend(exchange, routingKey, productLowStock);
    }
}
