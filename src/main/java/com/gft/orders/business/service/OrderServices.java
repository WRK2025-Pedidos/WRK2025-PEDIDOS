package com.gft.orders.business.service;

import com.gft.orders.business.model.DTO.ReturnLinesDTO;
import com.gft.orders.business.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

    UUID createOrder(Order order);

    Optional<Order> findOrderById(UUID id);

    List<Order> findAllOrders();

    BigDecimal createOrderReturn(UUID orderId);

    BigDecimal processReturnLines(UUID orderId, ReturnLinesDTO returnLinesDTO);
}
