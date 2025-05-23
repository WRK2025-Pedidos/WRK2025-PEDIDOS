package com.gft.orders.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineResponse(UUID product,
                                int quantity,
                                Double lineWeight,
                                BigDecimal productPrice,
                                BigDecimal linePrice) {
}
