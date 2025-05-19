
package com.gft.orders.application.service.impl;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderEntity;
import org.dozer.DozerBeanMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServicesImplTest {

    @Mock
    private DozerBeanMapper mapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServicesImpl orderServicesImpl;

    private OrderDTO orderDTO;
    private OrderEntity orderEntity1;

    @BeforeEach
    public void setUp() {
        initObjects();
    }

    @Test
    void shouldSaveOrder() {

        UUID uuid = UUID.randomUUID();

        when(mapper.map(orderDTO, OrderEntity.class)).thenReturn(orderEntity1);

        OrderEntity savedEntity = new OrderEntity();
        savedEntity.setId(uuid);

        when(orderRepository.save(orderEntity1)).thenReturn(savedEntity);

        UUID result = orderServicesImpl.createOrder(orderDTO);

        assertEquals(uuid, result);
    }

    /***************PRIVATE METHODS***********/
    private void initObjects() {

        orderDTO = Instancio.create(OrderDTO.class);
        orderEntity1 = Instancio.create(OrderEntity.class);
    }
}