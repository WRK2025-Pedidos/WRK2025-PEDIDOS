package com.gft.orders.business.service;

import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

    UUID createOrder(Order order);

    Optional<Order> findOrderById(UUID id);

    List<Order> findAllOrders();

    UUID createOrderReturn(UUID orderId, Map<UUID, ReturnLineDTO> orderReturnLines);
}
