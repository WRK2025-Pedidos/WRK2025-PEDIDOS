package com.gft.orders.application.service;

import com.gft.orders.domain.model.entity.OrderReturn;

import java.util.Optional;
import java.util.UUID;

public interface OrderReturnServices {

    Optional<OrderReturn> findOrderReturnById(UUID id);

}
