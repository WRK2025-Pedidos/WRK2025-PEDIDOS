package com.gft.orders.business.service.impl;

import com.gft.orders.business.config.exceptions.InvalidOrderStatusTransitionException;
import com.gft.orders.business.config.exceptions.InvalidReturnQuantityException;
import com.gft.orders.business.config.exceptions.OrderNotFoundException;
import com.gft.orders.business.config.exceptions.ReturnPeriodExceededException;
import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
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

        if(originalOrder.getOrderReturn()==true){
            throw new InvalidOrderStatusTransitionException("A returned order cannot be reactivated.");
        }

        if(orderJPARepository.idDateBeforeThirtyDays(orderId, LocalDateTime.now())) {
            throw new ReturnPeriodExceededException("Return period exceeded");
        }

        originalOrder.setOrderReturn(true);

        orderJPARepository.save(originalOrder);

        return originalOrder.getTotalPrice().negate();
    }

    @Override
    @Transactional
    public BigDecimal processReturnLines(ReturnLineDTO returnLineDTO) {
        OrderJPA originalOrder = orderJPARepository.findById(returnLineDTO.original_order())
                .orElseThrow(() -> new OrderNotFoundException("Original order not found"));

        if (originalOrder.getOrderReturn()) {
            throw new InvalidOrderStatusTransitionException("A returned order cannot be reactivated.");
        }

        if (orderJPARepository.idDateBeforeThirtyDays(returnLineDTO.original_order(), LocalDateTime.now())) {
            throw new ReturnPeriodExceededException("Return period exceeded");
        }

        Map<Long, Integer> returnLines = returnLineDTO.returnLines();

        for (Map.Entry<Long, Integer> entry : returnLines.entrySet()) {
            Long productId = entry.getKey();
            Integer quantityToReturn = entry.getValue();

            Optional<OrderLineJPA> orderLineOptional = originalOrder.getOrderLines().stream()
                    .filter(line -> line.getProduct().equals(productId))
                    .findFirst();

            if (orderLineOptional.isEmpty()) {
                throw new InvalidReturnQuantityException("Producto no encontrado en el pedido.");
            }

            OrderLineJPA orderLine = orderLineOptional.get();

            if (quantityToReturn > orderLine.getQuantity()) {
                throw new InvalidReturnQuantityException("La cantidad a devolver no puede ser mayor que la cantidad comprada.");
            }

            originalOrder.getReturnedProductQuantity().put(productId, quantityToReturn);

            orderLine.setQuantity(orderLine.getQuantity() - quantityToReturn);

            BigDecimal totalReturnAmount = orderLine.getProductPrice().multiply(BigDecimal.valueOf(quantityToReturn));
            originalOrder.setTotalPrice(originalOrder.getTotalPrice().subtract(totalReturnAmount));
        }

        orderJPARepository.save(originalOrder);

        return originalOrder.getTotalPrice().negate();
    }
}