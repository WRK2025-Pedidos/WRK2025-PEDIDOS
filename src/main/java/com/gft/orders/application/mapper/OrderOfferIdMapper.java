package com.gft.orders.application.mapper;

import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderOfferIdMapper {

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "offerId", source = "offerId")
    OrderOfferIdJPA toEntity(UUID orderId, UUID offerId);

}
