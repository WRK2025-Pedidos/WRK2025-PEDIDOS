package com.gft.orders.business.model;

import lombok.*;
import java.math.BigDecimal;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    private Long product;

    @Builder.Default
    private int quantity = 0;

    private Double lineWeight;
    private BigDecimal productPrice;
    private BigDecimal linePrice;
    private int returnedQuantity = 0;
}

