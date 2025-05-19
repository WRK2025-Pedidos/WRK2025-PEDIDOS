package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Generated
@Data
@Setter(AccessLevel.NONE)
@EqualsAndHashCode
public class OrderOfferIdJPA implements Serializable {

    @Column(name = "ORDER_ID")
    private UUID orderId;

    @Column(name = "OFFER_ID")
    private UUID offerId;

}
