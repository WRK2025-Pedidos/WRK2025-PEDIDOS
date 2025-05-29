package com.gft.orders.business.service.impl;

import com.gft.orders.business.config.OrderNotFoundException;
import com.gft.orders.business.config.ReturnPeriodExceededException;
import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderServices {

    private final OrderJPARepository orderJPARepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderJPARepository orderJPARepository, OrderMapper orderMapper) {
        this.orderJPARepository = orderJPARepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public UUID createOrder(Order order) {
        OrderJPA orderJPA = orderMapper.toOrderJPA(order);
        return orderJPARepository.save(orderJPA).getId();
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {
        return orderJPARepository.findById(id)
                .map(orderMapper::toOrderModel);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderJPARepository.findAll().stream()
                .map(orderMapper::toOrderModel)
                .toList();
    }

    @Override
    @Transactional
    public BigDecimal createOrderReturn(UUID orderId) {

        OrderJPA originalOrder = orderJPARepository.findById(orderId)
                                                    .orElseThrow(() -> new OrderNotFoundException("Original order not found"));

        if(orderJPARepository.idDateBeforeThirtyDays(orderId, LocalDateTime.now())) {
            throw new ReturnPeriodExceededException("Return period exceeded");
        }

        originalOrder.setOrderReturn(true);

        orderJPARepository.save(originalOrder);

        return originalOrder.getTotalPrice().negate();
    }
}