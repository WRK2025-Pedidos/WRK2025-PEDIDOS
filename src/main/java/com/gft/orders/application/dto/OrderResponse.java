package com.gft.orders.application.dto;

import com.gft.orders.domain.model.valueObject.OrderLine;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID cartId,
                            BigDecimal totalPrice,
                            Double countryTax,
                            Double paymentMethod,
                            LocalDateTime creationDate,
                            List<OrderLine> orderLines,
                            List<UUID> offers)
        implements Serializable {}
