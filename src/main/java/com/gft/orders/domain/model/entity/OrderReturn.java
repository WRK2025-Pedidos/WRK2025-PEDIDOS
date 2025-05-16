package com.gft.orders.domain.model.entity;

import com.gft.orders.domain.model.valueObject.OrderLines;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderReturn {

    UUID id;
    UUID orderId;
    BigDecimal totalPrice;
    Double countryTax;
    Double paymentMethod;
    LocalDateTime creationDate;
    List<OrderLines> orderLines;
    List<UUID> offers;

}
