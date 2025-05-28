package com.gft.orders.business.service.impl;

import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import com.gft.orders.integration.repositories.OrderLineJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderServices {

    private final OrderJPARepository orderJPARepository;
    private final OrderMapper orderMapper;
    private final OrderLineJPARepository orderLineJPARepository;

    public OrderServiceImpl(OrderJPARepository orderJPARepository, OrderMapper orderMapper, OrderLineJPARepository orderLineJPARepository) {
        this.orderJPARepository = orderJPARepository;
        this.orderMapper = orderMapper;
        this.orderLineJPARepository = orderLineJPARepository;
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
    public UUID createOrderReturn(UUID orderId, Map<UUID, ReturnLineDTO> orderReturnLines) {

        OrderJPA originalOrder = orderJPARepository.findById(orderId)
                                                    .orElseThrow(() -> new RuntimeException("Original order not found"));

        if(orderJPARepository.idDateBeforeThirtyDays(orderId)) {
            throw new RuntimeException("Return period exceeded");
        }

        OrderJPA baseOrder = orderJPARepository.isFirstReturn(orderId)
                            ? originalOrder
                            : orderJPARepository.findLastReturn(orderId);

        for (Map.Entry<UUID, ReturnLineDTO> entry : orderReturnLines.entrySet()) {
            UUID orderLineId = entry.getKey();
            Integer quantityToReturn = entry.getValue().returnLines().get(orderLineId);

            if(!orderLineJPARepository.enoughProductQuantity(orderLineId, quantityToReturn)) {
                throw new RuntimeException("Invalid quantity to return");
            }
        }

        List<OrderLine> returnLines = new ArrayList<>();

        for(Map.Entry<UUID, ReturnLineDTO> entry : orderReturnLines.entrySet()) {

            UUID orderLineId = entry.getKey();
            Integer quantityToReturn = entry.getValue().returnLines().get(orderLineId);

            OrderLineJPA originalLine = orderLineJPARepository.findById(orderLineId)
                    .orElseThrow(() -> new RuntimeException("Order line not found"));

            OrderLine returnLine = new OrderLine(
                    originalLine.getProduct(),
                    quantityToReturn,
                    originalLine.getLineWeight(),
                    originalLine.getProductPrice(),
                    originalLine.getProductPrice().multiply(BigDecimal.valueOf(quantityToReturn))
            );

            returnLines.add(returnLine);
        }

        Order returnOrder = new Order();
        returnOrder.setId(UUID.randomUUID());
        returnOrder.setCartId(baseOrder.getCartId());
        returnOrder.setCreationDate(LocalDateTime.now());
        returnOrder.setTotalPrice(calculateReturnTotal(calculateLinesTotal(returnLines), originalOrder.getCountryTax(), originalOrder.getPaymentMethod()));
        returnOrder.setCountryTax(originalOrder.getCountryTax());
        returnOrder.setPaymentMethod(originalOrder.getPaymentMethod());
        returnOrder.setOrderReturn(true);
        returnOrder.setParentOrderId(orderId);
        returnOrder.setOrderLines(returnLines);

        OrderJPA returnOrderJPA = orderMapper.toOrderJPA(returnOrder);
        return orderJPARepository.save(returnOrderJPA).getId();

    }

    @Override
    public BigDecimal calculateLineTotal(OrderLine orderLine) {
        return orderLine.getProductPrice().multiply(BigDecimal.valueOf(orderLine.getQuantity()));
    }

    @Override
    public BigDecimal calculateLinesTotal(List<OrderLine> orderLines) {

        return orderLines.stream()
                  .map((this::calculateLineTotal))
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean enoughProductQuantity(UUID orderLine, BigDecimal quantity) {
        return false;
    }

    @Override
    public BigDecimal calculateReturnTotal(BigDecimal totalOrderLines, double countryTax, double paymentTax) {

        BigDecimal multiplier = BigDecimal.valueOf(1 + countryTax + paymentTax);

        return totalOrderLines.multiply(multiplier);
    }

}