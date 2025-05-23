package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderLineRequest;
import com.gft.orders.application.dto.OrderLineResponse;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Generated
@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    OrderLine toDomain(OrderLineRequest request);
    OrderLineResponse toResponse(OrderLine domain);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "orderReturn", ignore = true)
    OrderLineJPAEntity toEntity(OrderLine domain);

    OrderLine toDomain(OrderLineJPAEntity domain);

}
