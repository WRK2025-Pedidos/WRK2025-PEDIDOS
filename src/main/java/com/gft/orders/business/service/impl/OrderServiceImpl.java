package com.gft.orders.business.service.impl;

import com.gft.orders.business.config.exceptions.InvalidOrderStatusTransitionException;
import com.gft.orders.business.config.exceptions.InvalidReturnQuantityException;
import com.gft.orders.business.config.exceptions.OrderNotFoundException;
import com.gft.orders.business.config.exceptions.ReturnPeriodExceededException;
import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.DTO.ReturnLinesDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
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

        if (originalOrder.getOrderReturn() == true) {
            throw new InvalidOrderStatusTransitionException("A returned order cannot be reactivated.");
        }

        if (orderJPARepository.idDateBeforeThirtyDays(orderId, LocalDateTime.now().minusDays(30))) {
            throw new ReturnPeriodExceededException("Return period exceeded");
        }

        originalOrder.setOrderReturn(true);

        orderJPARepository.save(originalOrder);

        return originalOrder.getTotalPrice().negate();
    }

    @Override
    @Transactional
    public BigDecimal processReturnLines(UUID orderId, ReturnLinesDTO returnLinesDTO) {
        OrderJPA originalOrder = orderJPARepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Original order not found"));

        if (orderJPARepository.idDateBeforeThirtyDays(orderId, LocalDateTime.now().minusDays(30))) {
            throw new ReturnPeriodExceededException("Return period exceeded");
        }

        BigDecimal totalRefundAmountForThisOperation = BigDecimal.ZERO;

        for (UUID orderLineId : returnLinesDTO.returnLines()) {
            Optional<OrderLineJPA> orderLineOptional = originalOrder.getOrderLines().stream()
                    .filter(line -> line.getId().equals(orderLineId))
                    .findFirst();

            if (orderLineOptional.isEmpty()) {
                throw new InvalidReturnQuantityException("Order line with ID " + orderLineId + " not found in the specified order.");
            }

            OrderLineJPA orderLine = orderLineOptional.get();

            if (orderLine.getReturnedQuantity() == orderLine.getQuantity()) {
                throw new InvalidReturnQuantityException("Order line with ID " + orderLineId + " has already been fully returned.");
            }

            int quantityToReturn = orderLine.getQuantity() - orderLine.getReturnedQuantity();

            if (quantityToReturn <= 0) {
                throw new InvalidReturnQuantityException("No quantity available to return for order line with ID " + orderLineId + ".");
            }

            orderLine.setReturnedQuantity(orderLine.getQuantity());

            totalRefundAmountForThisOperation = totalRefundAmountForThisOperation.add(orderLine.getLinePrice());

            originalOrder.getReturnedProductQuantity()
                    .merge(orderLine.getProduct(), quantityToReturn, Integer::sum);
        }

        originalOrder.setTotalPrice(originalOrder.getTotalPrice().subtract(totalRefundAmountForThisOperation));

        boolean isOrderFullyReturned = originalOrder.getOrderLines().stream()
                .allMatch(line -> line.getReturnedQuantity() == line.getQuantity());

        originalOrder.setOrderReturn(isOrderFullyReturned);

        orderJPARepository.save(originalOrder);

        return totalRefundAmountForThisOperation;
    }
}