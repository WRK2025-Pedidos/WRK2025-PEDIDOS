package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Generated
@Entity
@Table(name = "ORDER_OFFERS")
@Data
@Setter(AccessLevel.NONE)
public class OrderOfferJPAEntity {

    @EmbeddedId
    private OrderOfferIdJPA id;

}
