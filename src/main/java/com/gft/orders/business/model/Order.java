package com.gft.orders.business.model;

import com.gft.orders.business.config.InvalidOrderStatusTransitionException;
import lombok.*;
import org.dozer.Mapping;
import org.dozer.classmap.RelationshipType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Order {

    private UUID id;
    private UUID cartId;
    private LocalDateTime creationDate;

    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;

    private List<OrderLine> orderLines;

    private Boolean orderReturn;

    public void setOrderReturn(Boolean orderReturn) {

        if(this.orderReturn==true && orderReturn==false){
            throw new InvalidOrderStatusTransitionException("A returned order cannot be reactivated.");
        }

        this.orderReturn = orderReturn;
    }
}
