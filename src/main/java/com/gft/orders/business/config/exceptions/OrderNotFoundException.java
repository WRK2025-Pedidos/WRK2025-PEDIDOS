package com.gft.orders.business.config.exceptions;

public class OrderNotFoundException extends BusinessExceptions {
    public OrderNotFoundException(String message) {
        super(message);
    }
}