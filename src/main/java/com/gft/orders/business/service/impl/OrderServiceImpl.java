package com.gft.orders.business.service.impl;

import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServices {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public UUID createOrder(Order order) {
        OrderJPA orderJPA = orderMapper.toOrderJPA(order);
        return orderRepository.save(orderJPA).getId();
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderModel);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderModel)
                .toList();
    }

    /**
     * @param orderId
     * @param orderReturnLines
     * @return
     */
    @Override
    public UUID createOrderReturn(UUID orderId, Map<UUID, ReturnLineDTO> orderReturnLines) {
        return null;
    }

}