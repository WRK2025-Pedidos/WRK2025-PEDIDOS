package com.gft.orders.infraestructure.persistence.entity;

import com.gft.orders.domain.model.valueObject.OrderLines;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @Column()
    UUID id;
    UUID cartId;
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDate creationDate;
    List<OrderLines> orderLines;
    List<UUID> offers;

}
