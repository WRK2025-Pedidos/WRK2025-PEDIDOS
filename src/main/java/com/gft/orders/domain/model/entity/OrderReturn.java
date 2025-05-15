package com.gft.orders.domain.model.entity;

import com.gft.orders.domain.model.valueObject.OrderLines;
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
public class OrderReturn {

    UUID id;
    UUID orderId;
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDate creationDate;
    List<OrderLines> orderLines;
    List<UUID> offers;

}
