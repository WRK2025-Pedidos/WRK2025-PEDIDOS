package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Generated
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderOfferId implements Serializable {

    @Column(name = "ORDER_ID")
    private UUID orderId;

    @Column(name = "OFFER_ID")
    private UUID offerId;

}
