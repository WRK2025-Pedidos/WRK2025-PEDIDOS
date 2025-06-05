package com.gft.orders.offer.client.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Generated
@Getter
@Setter
@AllArgsConstructor
public class PromotionDTO {

    private Long id;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Double discount;
    private String promotionType;
    private Integer quantity;
    private String category;

}
