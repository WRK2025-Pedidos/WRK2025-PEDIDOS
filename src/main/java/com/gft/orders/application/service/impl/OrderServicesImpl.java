package com.gft.orders.application.service.impl;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.application.service.OrderServices;
import com.gft.orders.domain.model.Order;
import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import jakarta.transaction.Transactional;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServicesImpl implements OrderServices {

    private final OrderRepository orderRepository;
    private final DozerBeanMapper mapper;

    public OrderServicesImpl(OrderRepository orderRepository, DozerBeanMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UUID createOrder(OrderDTO orderDTO) {

        OrderJPAEntity orderJPAEntity = mapper.map(orderDTO, OrderJPAEntity.class);

        return orderRepository.save(orderJPAEntity).getId();
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {

        return orderRepository.findById(id).stream()
                .map(x -> mapper.map(x, Order.class))
                .findAny();
    }

    @Override
    public List<Order> findAllOrders() {

        return convertList(orderRepository.findAll());
    }


    /*******PRIVATE METHODS********/
    private List<Order> convertList(List<OrderJPAEntity> orderEntities) {

        return orderEntities.stream()
                .map(x -> mapper.map(x, Order.class))
                .toList();
    }
}
