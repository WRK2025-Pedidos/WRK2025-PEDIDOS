package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderRequest;
import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;

import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {OrderLineMapper.class, OrderOfferMapper.class})
public interface OrderMapper {

    @Mapping(target = "offers", ignore = true)
    Order toDomain(OrderRequest request);

    @Mapping(target = "offers", source = "offers")
    OrderResponse toResponse(Order domain);

    @Mapping(target = "offers", ignore = true)
    OrderJPAEntity toEntity(Order domain);

    @AfterMapping
    default void mapOffers(@MappingTarget OrderJPAEntity entity, Order domain, @Context OrderOfferMapper offerMapper) {
        if (domain.getOffers() != null) {
            List<OrderOfferJPAEntity> offers = domain.getOffers().stream()
                                            .map(offerId -> offerMapper.toEntity(offerId, entity))
                                            .collect(Collectors.toList());

            entity.setOffers(offers);
        }
    }
    @Mapping(target = "offers", ignore = true)
    Order toDomain(OrderJPAEntity entity);

    @AfterMapping
    default void mapOffersToDomain(@MappingTarget Order domain, OrderJPAEntity entity) {

        if (entity.getOffers() != null) {
            List<UUID> offerIds = entity.getOffers().stream()
                                .map(offerEntity -> offerEntity.getId().getOfferId())
                                .toList();
        }
    }
}
