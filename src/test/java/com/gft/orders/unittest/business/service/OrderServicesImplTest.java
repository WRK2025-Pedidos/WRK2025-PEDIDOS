package com.gft.orders.unittest.business.service;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.impl.OrderServiceImpl;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderRepository;
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
    private OrderServiceImpl orderServicesImpl;

    private Order order1;
    private Order order2;
    private OrderJPA orderJPA1;
    private OrderJPA orderJPA2;

    @BeforeEach
    public void setUp() {
        initObjects();
    }

    @Test
    void shouldSaveOrder() {

        UUID uuid = UUID.randomUUID();

        when(mapper.map(order1, OrderJPA.class)).thenReturn(orderJPA1);

        OrderJPA savedEntity = new OrderJPA();
        savedEntity.setId(uuid);

        when(orderRepository.save(orderJPA1)).thenReturn(savedEntity);

        UUID result = orderServicesImpl.createOrder(order1);

        assertEquals(uuid, result);
    }

    @Test
    void shouldFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderRepository.findById(uuid)).thenReturn(Optional.of(orderJPA1));
        when(mapper.map(orderJPA1, Order.class)).thenReturn(order1);

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

        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderJPA1, orderJPA2));
        when(mapper.map(orderJPA1, Order.class)).thenReturn(order1);
        when(mapper.map(orderJPA2, Order.class)).thenReturn(order2);

        List<Order> result = orderServicesImpl.findAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(ordersExpected));
    }

    /***************PRIVATE METHODS***********/
    private void initObjects() {

        order1 = Instancio.create(Order.class);
        order2 = Instancio.create(Order.class);
        orderJPA1 = Instancio.create(OrderJPA.class);
        orderJPA2 = Instancio.create(OrderJPA.class);
    }
}