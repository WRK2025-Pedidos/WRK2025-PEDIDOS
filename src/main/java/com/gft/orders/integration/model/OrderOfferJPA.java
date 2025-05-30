package com.gft.orders.integration.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Generated
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_offers")
public class OrderOfferJPA {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "offer_id")
    private Long offerId;

}
