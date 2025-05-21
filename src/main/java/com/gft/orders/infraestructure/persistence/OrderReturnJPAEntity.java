package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_returns")
@Data
public class OrderReturnJPAEntity {

    @Id
    private UUID id;

    private UUID orderId;
    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;
    private LocalDateTime creationDate;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_return_id"))
    private List<OrderLineJPAEntity> orderLines;

    @OneToMany
    private List<OrderOfferJPAEntity> offers;
}

