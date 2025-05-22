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

    @Mapping(target = "product", source = "product")
    OrderLine toDomain(OrderLineRequest request);

    @Mapping(target = "product", source = "product")
    OrderLineResponse toResponse(OrderLine domain);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "orderReturn", ignore = true)
    OrderLineJPAEntity toEntity(OrderLine domain);

    @Mapping(target = "product", source = "product")
    OrderLine toDomain(OrderLineJPAEntity domain);

}
