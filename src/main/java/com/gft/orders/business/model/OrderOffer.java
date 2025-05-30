package com.gft.orders.business.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOffer {

    private UUID orderId;
    private Long offerId;

}
