package com.gft.orders.infraestructure.persistence;

import com.gft.orders.domain.model.valueObject.OrderLine;
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
    UUID id;

    UUID orderId;
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDateTime creationDate;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_return_id"))
    List<OrderLineJPAEntity> orderLines;

    @OneToMany
    List<OrderOfferJPAEntity> offers;
}
