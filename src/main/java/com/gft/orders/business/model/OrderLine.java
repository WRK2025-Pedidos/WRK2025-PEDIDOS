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

    private UUID id;
    private Long product;

    @Builder.Default
    private int quantity = 0;

    private Double lineWeight;
    private BigDecimal linePrice;
    private int returnedQuantity = 0;
}

