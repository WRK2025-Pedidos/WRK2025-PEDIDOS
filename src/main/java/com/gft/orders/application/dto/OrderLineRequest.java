package com.gft.orders.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderLineRequest {

    @NotNull
    private UUID product;

    @NotNull
    private int quantity;

    @NotNull
    private Double lineWeight;

    @NotNull
    private BigDecimal productPrice;

    @NotNull
    private BigDecimal linePrice;
}
