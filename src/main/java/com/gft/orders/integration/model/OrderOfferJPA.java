package com.gft.orders.integration.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Generated
@Entity
@Table(name = "order_offers")
@Data
@Setter(AccessLevel.NONE)
public class OrderOfferJPAEntity {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "offer_id")
    private UUID offerId;
}
