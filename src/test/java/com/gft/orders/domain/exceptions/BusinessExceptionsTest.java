package com.gft.orders.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessExceptionsTest {
    @Test
    void businessExceptionThrown_Test() {
        String message = "Business rule violated";
        try {
            throw new BusinessExceptions(message);
        } catch (BusinessExceptions ex) {
            assertEquals(message, ex.getMessage());
        }
    }

}
