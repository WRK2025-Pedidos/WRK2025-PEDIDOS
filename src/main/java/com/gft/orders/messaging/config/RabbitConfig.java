package com.gft.orders.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_PRODUCT = "orders";

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(EXCHANGE_PRODUCT);
    }

    @Bean
    public Queue stockLowQueue() {
        return new Queue("products.orders.stock.low");
    }

    @Bean
    public Binding bindingStockLowQueue(Queue stockLowQueue, DirectExchange productExchange) {
        return BindingBuilder.bind(stockLowQueue).to(productExchange).with("stock.low");
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setObservationEnabled(true);
        return template;
    }
}
