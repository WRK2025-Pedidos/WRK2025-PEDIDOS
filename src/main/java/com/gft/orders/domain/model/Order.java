package com.gft.orders.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Order {

    UUID id;
    UUID cartId;
    Double totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDate creationDate;
    List<OrderLines> orderLines;
    List<UUID> offers;

}
