package com.gft.orders.integrationtest.messaging;

import com.gft.orders.messaging.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(RabbitConfig.class)
@SpringBootTest
public class RabbitConfigIT {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired(required = false)
    private TopicExchange productExchange;

    @Autowired(required = false)
    private MessageConverter messageConverter;

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        assertNotNull(rabbitConfig);
    }

    @Test
    void productExchangeBeanShouldBeCreated() {
        assertNotNull(productExchange);
        assertEquals(RabbitConfig.EXCHANGE_PRODUCT, productExchange.getName());
    }

    @Test
    void messageConverterBeanShouldBeCreated() {
        assertNotNull(messageConverter);
    }

    @Test
    void rabbitTemplateBeanShouldBeCreated() {
        assertNotNull(rabbitTemplate);
        assertSame(messageConverter, rabbitTemplate.getMessageConverter());
    }
}