package com.gft.orders.application.service;

import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.domain.model.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

    UUID createOrder(OrderResponse orderResponse);

    Optional<Order> findOrderById(UUID id);

    List<Order> findAllOrders();
}
