package com.gft.orders.business.config;

public class InvalidOrderStatusTransitionException extends BusinessExceptions{
    public InvalidOrderStatusTransitionException(String message){
        super(message);
    }
}
