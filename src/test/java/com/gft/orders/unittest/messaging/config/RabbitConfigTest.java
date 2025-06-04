package com.gft.orders.unittest.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.orders.messaging.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitConfigTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ConnectionFactory connectionFactory;

    @InjectMocks
    private RabbitConfig rabbitConfig;

    @Test
    void productExchange_shouldReturnTopicExchangeWithCorrectName() {
        DirectExchange exchange = rabbitConfig.productExchange();

        assertNotNull(exchange);
        assertEquals(RabbitConfig.EXCHANGE_PRODUCT, exchange.getName());
        assertTrue(exchange.isDurable());
        assertFalse(exchange.isAutoDelete());
    }

    @Test
    void jsonMessageConverter_shouldReturnJacksonConverterWithProvidedObjectMapper() {
        MessageConverter converter = rabbitConfig.jsonMessageConverter(objectMapper);

        assertNotNull(converter);
        assertTrue(converter instanceof Jackson2JsonMessageConverter);
    }

    @Test
    void rabbitTemplate_shouldConfigureTemplateWithConnectionFactoryAndMessageConverter() {
        MessageConverter mockConverter = mock(MessageConverter.class);

        RabbitTemplate template = rabbitConfig.rabbitTemplate(connectionFactory, mockConverter);

        assertNotNull(template);
        assertEquals(connectionFactory, template.getConnectionFactory());
        assertEquals(mockConverter, template.getMessageConverter());
    }

    @Test
    void rabbitTemplate_shouldUseJsonMessageConverterWhenCreated() {
        MessageConverter converter = rabbitConfig.jsonMessageConverter(objectMapper);

        RabbitTemplate template = rabbitConfig.rabbitTemplate(connectionFactory, converter);

        assertNotNull(template);
        assertEquals(converter, template.getMessageConverter());
    }

    @Test
    void constants_shouldHaveCorrectValues() {
        assertEquals("orders", RabbitConfig.EXCHANGE_PRODUCT);
    }
}