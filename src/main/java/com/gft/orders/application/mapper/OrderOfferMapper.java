package com.gft.orders.application.mapper;

import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {OrderOfferIdMapper.class})
public interface OrderOfferMapper {

    @Mapping(target = "id", expression = "java(orderOfferIdMapper.toEntity(order.getId(), offerId))")
    @Mapping(target = "order", source = "order")
    OrderOfferJPAEntity toEntity(UUID offerId, OrderJPAEntity order, @Context OrderOfferIdMapper orderOfferIdMapper);

}
