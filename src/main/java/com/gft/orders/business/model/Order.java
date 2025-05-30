package com.gft.orders.business.model;

import lombok.*;
import org.dozer.Mapping;
import org.dozer.classmap.RelationshipType;

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
    private UUID cartId;
    private LocalDateTime creationDate;

    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;

    private List<OrderLine> orderLines;
    private List<OrderOffer> orderOffers;

    private Boolean orderReturn;

}
