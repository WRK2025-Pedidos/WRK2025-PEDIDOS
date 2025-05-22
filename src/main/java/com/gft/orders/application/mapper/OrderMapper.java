package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderRequest;
import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {OrderLineMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderLines", source = "orderLines")
    @Mapping(target = "offers", source = "offers")
    Order toDomain(OrderRequest request);

    @Mapping(target = "orderLines", source = "orderLines")
    @Mapping(target = "offers", source = "offers")
    OrderResponse toResponse(Order domain);

    @Mapping(target = "orderLines", source = "orderLines")
    @Mapping(target = "offers", source = "offers")
    OrderJPAEntity toEntity(Order domain);

    @Mapping(target = "orderLines", source = "orderLines")
    @Mapping(target = "offers", source = "offers")
    Order toDomain(OrderJPAEntity entity);
}
