package com.gft.orders.application.mapper;

import com.gft.orders.domain.model.entity.OrderReturn;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderReturnMapper {

    OrderReturnJPAEntity toEntity(OrderReturn domain);
    OrderReturn toDomain(OrderReturnJPAEntity entity);
}