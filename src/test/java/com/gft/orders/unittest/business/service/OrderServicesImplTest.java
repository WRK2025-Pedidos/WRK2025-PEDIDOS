package com.gft.orders.unittest.business.service;

import com.gft.orders.business.config.InvalidOrderStatusTransitionException;
import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.impl.OrderServiceImpl;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServicesImplTest {

    @Mock
    private OrderMapper mapper;

    @Mock
    private OrderJPARepository orderJPARepository;

    @InjectMocks
    private OrderServiceImpl orderServicesImpl;

    private Order order1;
    private Order order2;
    private OrderJPA orderJPA1;
    private OrderJPA orderJPA2;
    private OrderJPA originalOrder;
    private UUID orderId;

    @BeforeEach
    public void setUp() {
        initObjects();
    }

    @Test
    void shouldSaveOrder() {

        UUID uuid = UUID.randomUUID();

        when(mapper.toOrderJPA(order1)).thenReturn(orderJPA1);

        OrderJPA savedEntity = new OrderJPA();
        savedEntity.setId(uuid);

        when(orderJPARepository.save(orderJPA1)).thenReturn(savedEntity);

        UUID result = orderServicesImpl.createOrder(order1);

        assertEquals(uuid, result);
    }

    @Test
    void shouldFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderJPARepository.findById(uuid)).thenReturn(Optional.of(orderJPA1));
        when(mapper.toOrderModel(orderJPA1)).thenReturn(order1);

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isPresent());
        assertEquals(order1, optional.get());
    }

    @Test
    void shouldNotFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderJPARepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isEmpty());
    }

    @Test
    void findAllOrders() {

        List<Order> ordersExpected = Arrays.asList(order1, order2);

        when(orderJPARepository.findAll()).thenReturn(Arrays.asList(orderJPA1, orderJPA2));
        when(mapper.toOrderModel(orderJPA1)).thenReturn(order1);
        when(mapper.toOrderModel(orderJPA2)).thenReturn(order2);

        List<Order> result = orderServicesImpl.findAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(ordersExpected));
    }

    @Test
    void createOrderReturn_success() {

        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));

        when(orderJPARepository.idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class))).thenReturn(false);

        when(orderJPARepository.save(any(OrderJPA.class))).thenReturn(originalOrder);

        BigDecimal result = orderServicesImpl.createOrderReturn(orderId);

        assertTrue(originalOrder.getOrderReturn());
        assertEquals(new BigDecimal("-100.00"), result);

        verify(orderJPARepository).findById(orderId);
        verify(orderJPARepository).save(originalOrder);
        verify(orderJPARepository).idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class));
    }

    @Test
    void createOrderReturn_failure() {

        when(orderJPARepository.findById(orderId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderServicesImpl.createOrderReturn(orderId));

        assertEquals("Original order not found", exception.getMessage());
    }

    @Test
    void createOrderReturn_ReturnPeriodExceeded(){

        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));

        when(orderJPARepository.idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class))).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderServicesImpl.createOrderReturn(orderId));

        assertEquals("Return period exceeded", exception.getMessage());
    }

    @Test
    void createOrderReturn_InvalidStatusTransition(){

        originalOrder.setOrderReturn(true);

        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));

        InvalidOrderStatusTransitionException exception = assertThrows(InvalidOrderStatusTransitionException.class, () ->
                orderServicesImpl.createOrderReturn(orderId));

        assertEquals("A returned order cannot be reactivated.", exception.getMessage());
    }

    /***************PRIVATE METHODS***********/
    private void initObjects() {

        order1 = Instancio.create(Order.class);
        order2 = Instancio.create(Order.class);
        orderJPA1 = Instancio.create(OrderJPA.class);
        orderJPA2 = Instancio.create(OrderJPA.class);

        orderId = UUID.randomUUID();
        originalOrder = new OrderJPA();
        originalOrder.setId(orderId);
        originalOrder.setTotalPrice(new BigDecimal("100.00"));
        originalOrder.setOrderReturn(false);
    }
}