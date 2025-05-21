package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Generated
@Entity
@Table(name = "order_offers")
@Data
@Setter(AccessLevel.NONE)
public class OrderOfferJPAEntity {

    @EmbeddedId
    private OrderOfferIdJPA id;

    @ManyToOne(optional = false)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJPAEntity order;

}
