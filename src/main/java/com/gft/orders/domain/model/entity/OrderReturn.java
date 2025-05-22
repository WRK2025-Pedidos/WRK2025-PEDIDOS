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
@Builder
@EqualsAndHashCode(of = "id")
public class OrderReturn {

    UUID id;
    UUID orderId;
    BigDecimal totalPrice;
    LocalDateTime creationDate;
    List<OrderLine> orderLines;

}
