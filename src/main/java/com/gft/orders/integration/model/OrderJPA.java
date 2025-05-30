package com.gft.orders.integration.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class OrderJPA {

    @Id
    private UUID id;

    private UUID cartId;
    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;
    private LocalDateTime creationDate;
  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLineJPA> orderLines;

    private Boolean orderReturn;

}
