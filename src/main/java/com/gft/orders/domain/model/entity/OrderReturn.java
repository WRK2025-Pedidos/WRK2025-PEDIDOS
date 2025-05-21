package com.gft.orders.domain.model.entity;

import com.gft.orders.domain.model.valueObject.OrderLine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Generated
@Data
@Builder
@EqualsAndHashCode(of = "id")
@Schema(description = "Represents a return for an order")
public class OrderReturn {

    UUID id;
    UUID orderId;
    BigDecimal totalPrice;
    LocalDateTime creationDate;
    List<OrderLine> orderLines;

}
