package com.gft.orders.unittest.exceptions;

import com.gft.orders.domain.exceptions.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {
    @Test
    public void errorResponseThrown_Test() {
        String message = "Error message";
        ErrorResponse errorResponse = new ErrorResponse(message);

        assertEquals(message, errorResponse.getError());

    }

}