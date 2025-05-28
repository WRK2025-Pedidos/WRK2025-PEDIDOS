package com.gft.orders.integration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLineJPA {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderJPA order;

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID product;
    private int quantity;
    private Double lineWeight;
    private BigDecimal productPrice;
    private BigDecimal linePrice;
}