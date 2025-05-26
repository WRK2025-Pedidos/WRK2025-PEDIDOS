package com.gft.orders.business.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    private UUID product;
    private int quantity;
    private Double lineWeight;
    private BigDecimal productPrice;
    private BigDecimal linePrice;

}

