package com.gft.orders.offer.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDto {

    private Long id;
    private String type;
    private String category;
    private Integer quantity;

}
