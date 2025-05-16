package com.gft.orders.infraestructure.persistence;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "ORDER_OFFERS")
@Getter
@NoArgsConstructor
public class OrderOfferEntity {

    @EmbeddedId
    private OrderOfferId id;

}
