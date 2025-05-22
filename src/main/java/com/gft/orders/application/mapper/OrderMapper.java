package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderRequest;
import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {OrderLineMapper.class})
public interface OrderMapper {

    Order toDomain(OrderRequest request);
    OrderResponse toResponse(Order domain);

    OrderJPAEntity toEntity(Order domain);
    Order toDomain(OrderJPAEntity entity);
}
