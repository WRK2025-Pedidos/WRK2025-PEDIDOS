package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderLineRequest;
import com.gft.orders.application.dto.OrderLineResponse;
import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    OrderLine toDomain(OrderLineRequest request);
    OrderLineResponse toResponse(OrderLine domain);

    OrderLineJPAEntity toEntity(OrderLine domain);
    OrderLine toDomain(OrderLineJPAEntity domain);

}
