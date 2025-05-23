package com.gft.orders.application.service.impl;

import com.gft.orders.application.service.OrderReturnServices;
import com.gft.orders.domain.model.entity.OrderReturn;
import com.gft.orders.domain.repository.OrderReturnRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderReturnServicesImpl implements OrderReturnServices {

    private final OrderReturnRepository orderReturnRepository;
    private final DozerBeanMapper mapper;

    public OrderReturnServicesImpl(OrderReturnRepository orderReturnRepository, DozerBeanMapper mapper) {
        this.orderReturnRepository = orderReturnRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<OrderReturn> findOrderReturnById(UUID id) {

        return orderReturnRepository.findById(id).stream()
                .map(x -> mapper.map(x, OrderReturn.class))
                .findAny();
    }

}
