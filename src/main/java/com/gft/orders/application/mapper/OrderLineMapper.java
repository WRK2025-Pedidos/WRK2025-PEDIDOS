package com.gft.orders.application.mapper;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    @Mapping(target = "productId", source = "product")
    OrderLine toDomain(OrderLineRequest request);

    @Mapping(target = "product", source = "productId")
    OrderLineResponse toResponse(OrderLine domain);

    @Mapping(target = "product", source = "productId")
    OrderLineJPAEntity toEntity(OrderLine domain);

    @Mapping(target = "productId", source = "product")
    OrderLine toDomain(OrderLineJPAEntity domain);

}
