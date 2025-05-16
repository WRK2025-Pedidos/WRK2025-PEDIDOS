package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Generated;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Embeddable
public class OrderLineEntity {

    @Column(name = "PRODUCT", nullable = false)
    private UUID product;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "LINE_WEIGHT", nullable = false)
    private Double lineWeight;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "LINE_PRICE", nullable = false)
    private BigDecimal linePrice;
}