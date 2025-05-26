package com.gft.orders.presentation.config;

import lombok.Getter;

@Getter
public class ErrorResponse {

    final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

}
