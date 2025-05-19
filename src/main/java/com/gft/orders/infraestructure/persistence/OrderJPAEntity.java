package com.gft.orders.infraestructure.persistence;

import com.gft.orders.domain.model.valueObject.OrderLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Entity
@Table(name = "ORDERS")
@Data
@EqualsAndHashCode(of = "id")
public class OrderJPAEntity {

    @Id
    UUID id;

    UUID cartId;

    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;

    LocalDateTime creationDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_LINES", joinColumns = @JoinColumn(name = "ORDER_ID"))
    List<OrderLine> orderLines;

    @OneToMany
    List<OrderOfferJPAEntity> offers;

}
