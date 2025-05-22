package com.gft.orders.application.mapper;

import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {OrderOfferIdMapper.class})
public interface OrderOfferMapper {

    @Mapping(target = "id.orderId", source = "order.id")
    @Mapping(target = "id.offerId", source = "offerId")
    @Mapping(target = "order", source = "order")
    OrderOfferJPAEntity toEntity(UUID offerId, OrderJPAEntity order);

    default UUID toOfferId(OrderOfferJPAEntity entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }
        return entity.getId().getOfferId();
    }
}
