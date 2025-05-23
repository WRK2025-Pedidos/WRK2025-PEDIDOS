package com.gft.orders.application.service;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

    UUID createOrder(OrderDTO orderDTO);

    Optional<Order> findOrderById(UUID id);

    List<Order> findAllOrders();
}
