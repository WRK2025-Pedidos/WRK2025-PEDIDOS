package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineJPAEntity {

    private UUID product;
    private int quantity;
    private Double lineWeight;
    private BigDecimal productPrice;
    private BigDecimal linePrice;

}