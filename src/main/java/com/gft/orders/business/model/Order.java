package com.gft.orders.business.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Order {

    private UUID id;
    private UUID userId;
    private LocalDateTime creationDate;

    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;

    private List<OrderLine> orderLines;
    private List<OrderOffer> orderOffers;

    private Boolean orderReturn;

    private Map<Long, Integer> returnedProductQuantity = new HashMap<>();
}
