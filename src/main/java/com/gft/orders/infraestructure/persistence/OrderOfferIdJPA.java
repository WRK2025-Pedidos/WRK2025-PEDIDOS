package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Generated
@Data
@EqualsAndHashCode
@Embeddable
public class OrderOfferIdJPA implements Serializable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "offer_id")
    private UUID offerId;

}
