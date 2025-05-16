package com.gft.orders.domain.model.entity;

import com.gft.orders.domain.model.valueObject.OrderLine;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDate creationDate;
    List<OrderLine> orderLines;
    List<UUID> offers;

}
