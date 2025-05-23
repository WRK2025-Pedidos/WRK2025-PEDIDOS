package com.gft.orders.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Getter
@Setter
public class OrderRequest {

    @NotNull
    private UUID id;

    @NotNull
    private UUID cartId;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private Double countryTax;

    @NotNull
    private Double paymentMethod;

    @NotNull
    private LocalDateTime creationDate;

    @NotEmpty
    private List<OrderLineRequest> orderLines;

    private List<UUID> offers;

}
