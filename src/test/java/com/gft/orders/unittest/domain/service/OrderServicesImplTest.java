package com.gft.orders.unittest.domain.service;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.application.service.impl.OrderServicesImpl;
import com.gft.orders.domain.model.Order;
import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import org.dozer.DozerBeanMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServicesImplTest {

    @Mock
    private DozerBeanMapper mapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServicesImpl orderServicesImpl;

    private Order order1;
    private Order order2;
    private OrderDTO orderDTO;
    private OrderJPAEntity orderJPAEntity1;
    private OrderJPAEntity orderJPAEntity2;

    @BeforeEach
    public void setUp() {
        initObjects();
    }

    @Test
    void shouldSaveOrder() {

        UUID uuid = UUID.randomUUID();

        when(mapper.map(orderDTO, OrderJPAEntity.class)).thenReturn(orderJPAEntity1);

        OrderJPAEntity savedEntity = new OrderJPAEntity();
        savedEntity.setId(uuid);

        when(orderRepository.save(orderJPAEntity1)).thenReturn(savedEntity);

        UUID result = orderServicesImpl.createOrder(orderDTO);

        assertEquals(uuid, result);
    }

    @Test
    void shouldFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderRepository.findById(uuid)).thenReturn(Optional.of(orderJPAEntity1));
        when(mapper.map(orderJPAEntity1, Order.class)).thenReturn(order1);

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isPresent());
        assertEquals(order1, optional.get());
    }

    @Test
    void shouldNotFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderRepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isEmpty());
    }

    @Test
    void findAllOrders() {

        List<Order> ordersExpected = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderJPAEntity1, orderJPAEntity2));
        when(mapper.map(orderJPAEntity1, Order.class)).thenReturn(order1);
        when(mapper.map(orderJPAEntity2, Order.class)).thenReturn(order2);

        List<Order> result = orderServicesImpl.findAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(ordersExpected));
    }

    /***************PRIVATE METHODS***********/
    private void initObjects() {

        order1 = Instancio.create(Order.class);
        order2 = Instancio.create(Order.class);
        orderDTO = Instancio.create(OrderDTO.class);
        orderJPAEntity1 = Instancio.create(OrderJPAEntity.class);
        orderJPAEntity2 = Instancio.create(OrderJPAEntity.class);
    }
}