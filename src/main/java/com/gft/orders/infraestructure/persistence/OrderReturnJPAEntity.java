package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Generated
@Entity
@Table(name = "ORDER_RETURNS")
@Data
@EqualsAndHashCode(of = "id")
public class OrderReturnJPAEntity {

    @Id
    UUID id;

    UUID orderId;
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDateTime creationDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_LINES", joinColumns = @JoinColumn(name = "ORDER_RETURN_ID"))
    List<OrderLineJPAEntity> orderLines;

    @OneToMany
    List<OrderOfferJPAEntity> offers;
}
