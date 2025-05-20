package com.gft.orders.infraestructure.persistence;

import com.gft.orders.domain.model.valueObject.OrderLine;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDER_RETURNS")
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
    @CollectionTable(name = "ORDER_LINES", joinColumns = @JoinColumn(name = "ORDER_RETURN_ID"))
    List<OrderLineJPAEntity> orderLines;

    @OneToMany
    List<OrderOfferJPAEntity> offers;
}
