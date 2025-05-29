package com.gft.orders.business.model;

import lombok.*;
import java.util.UUID;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOffer {

    private UUID orderId;
    private UUID offerId;

}
