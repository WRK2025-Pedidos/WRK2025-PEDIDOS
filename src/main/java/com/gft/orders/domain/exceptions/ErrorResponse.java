package com.gft.orders.domain.exceptions;

import lombok.Getter;

@Getter
public class ErrorResponse {

    final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

}
