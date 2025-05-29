package com.gft.orders.business.config;

public class OrderNotFoundException extends BusinessExceptions {
    public OrderNotFoundException(String message) {
        super(message);
    }
}