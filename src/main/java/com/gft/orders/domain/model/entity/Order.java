package com.gft.orders.domain.model.entity;

import com.gft.orders.domain.model.valueObject.OrderLine;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
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
    LocalDateTime creationDate;
    List<OrderLine> orderLine;
    List<UUID> offers;

}
