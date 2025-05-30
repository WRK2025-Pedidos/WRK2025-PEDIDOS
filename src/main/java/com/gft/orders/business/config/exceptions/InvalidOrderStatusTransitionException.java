package com.gft.orders.business.config.exceptions;

public class InvalidOrderStatusTransitionException extends BusinessExceptions {
    public InvalidOrderStatusTransitionException(String message){
        super(message);
    }
}
