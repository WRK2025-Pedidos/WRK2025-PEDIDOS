package com.gft.orders.infraestructure.persistence;

import com.gft.orders.domain.model.valueObject.OrderLines;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderEntity {

    @Id
    UUID id;

    UUID cartId;

    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;

    LocalDateTime creationDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_LINES", joinColumns = @JoinColumn(name = "ORDER_ID"))
    List<OrderLines> orderLines;

    @OneToMany
    List<OrderOfferEntity> offers;

}
