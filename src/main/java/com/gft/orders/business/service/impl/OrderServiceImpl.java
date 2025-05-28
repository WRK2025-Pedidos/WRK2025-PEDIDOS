package com.gft.orders.business.service.impl;

import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    /**
     * @param orderId
     * @param orderReturnLines
     * @return
     */
    @Override
    public UUID createOrderReturn(UUID orderId, Map<UUID, ReturnLineDTO> orderReturnLines) {
        return null;
    }

    /**
     * @param orderLine
     * @return
     */
    @Override
    public BigDecimal calculateLineTotal(OrderLine orderLine) {
        return orderLine.getProductPrice().multiply(BigDecimal.valueOf(orderLine.getQuantity()));
    }

    /**
     * @param orderLines
     * @return
     */
    @Override
    public BigDecimal calculateLinesTotal(List<OrderLine> orderLines) {

        return orderLines.stream()
                  .map((this::calculateLineTotal))
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}