package com.gft.orders.application.mapper;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {OrderLineMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toDomain(OrderRequest request);
    OrderResponse toResponse(Order domain);

    OrderJPAEntity toEntity(Order domain);
    Order toDomain(OrderJPAEntity entity);
}
