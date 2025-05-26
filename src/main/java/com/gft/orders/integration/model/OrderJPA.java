package com.gft.orders.integration.model;

import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Generated
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(of = "id")
public class OrderJPAEntity {

    @Id
    private UUID id;

    private UUID cartId;
    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;
    private LocalDateTime creationDate;
  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLineJPAEntity> orderLines;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<OrderOfferJPAEntity> offers;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderReturnJPAEntity> returns = new ArrayList<>();

    public void addOrderLine(OrderLineJPAEntity orderLine) {

        orderLine.setOrder(this);
        this.orderLines.add(orderLine);
    }
}
