package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_returns")
@Getter
@Setter
@NoArgsConstructor
public class OrderReturnJPAEntity {

    @Id
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderJPAEntity order;

    BigDecimal totalPrice;
    LocalDateTime creationDate;

    @OneToMany(mappedBy = "orderReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLineJPAEntity> returnLines = new ArrayList<>();

    public void addReturnLine(OrderLineJPAEntity orderLine) {

        orderLine.setOrderReturn(this);
        this.returnLines.add(orderLine);
    }
}
