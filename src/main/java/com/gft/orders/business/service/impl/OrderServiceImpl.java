package com.gft.orders.business.service.impl;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServices {

    private final OrderRepository orderRepository;
    private final DozerBeanMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, DozerBeanMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    /**
     * @param order
     * @return
     */
    @Override
    @Transactional
    public UUID createOrder(Order order) {

        OrderJPA orderJPA = mapper.map(order, OrderJPA.class);

        return orderRepository.save(orderJPA).getId();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Order> findOrderById(UUID id) {

        return orderRepository.findById(id).stream()
                .map(x -> mapper.map(x, Order.class))
                .findAny();
    }

    /**
     * @return
     */
    @Override
    public List<Order> findAllOrders() {

        return convertList(orderRepository.findAll());
    }


    /********PRIVATE METHODS*******/

    private List<Order> convertList(List<OrderJPA> orderEntities) {

        return orderEntities.stream()
                .map(x -> mapper.map(x, Order.class))
                .toList();
    }

}
