package com.gft.orders.domain.model;

import com.gft.orders.domain.model.valueObject.OrderLine;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of="id")
public class Order {

    private final UUID id;
    private final UUID cartId;
    private final LocalDateTime creationDate;

    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;
    private List<UUID> offers;

    private List<OrderLine> orderLines;

}
