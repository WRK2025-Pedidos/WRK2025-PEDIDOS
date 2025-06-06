package com.gft.orders.unittest.business.service;

import com.gft.orders.business.config.exceptions.InvalidOrderStatusTransitionException;
import com.gft.orders.business.config.exceptions.InvalidReturnQuantityException;
import com.gft.orders.business.config.exceptions.OrderNotFoundException;
import com.gft.orders.business.config.exceptions.ReturnPeriodExceededException;
import com.gft.orders.business.mapper.OrderMapper;
// Using the correct DTO name: ReturnLinesDTO from your provided test class
import com.gft.orders.business.model.DTO.ReturnLinesDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.impl.OrderServiceImpl;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
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
import java.util.*;
import java.util.stream.Collectors;

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
        verify(orderJPARepository).save(orderJPA1);
    }

    @Test
    void shouldFindOrderById() {
        UUID uuid = UUID.randomUUID();
        when(orderJPARepository.findById(uuid)).thenReturn(Optional.of(orderJPA1));
        when(mapper.toOrderModel(orderJPA1)).thenReturn(order1);

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isPresent());
        assertEquals(order1, optional.get());
        verify(orderJPARepository).findById(uuid);
        verify(mapper).toOrderModel(orderJPA1);
    }

    @Test
    void shouldNotFindOrderById() {
        UUID uuid = UUID.randomUUID();
        when(orderJPARepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<Order> optional = orderServicesImpl.findOrderById(uuid);

        assertTrue(optional.isEmpty());
        verify(orderJPARepository).findById(uuid);
    }

    @Test
    void shouldProcessReturnLinesSuccessfully() {

        UUID orderLineId = UUID.randomUUID();
        Long productId = 123L;
        int purchasedQuantity = 10;
        OrderLineJPA orderLineJPA = getOrderLineJPA(orderLineId, productId, purchasedQuantity);

        originalOrder.setOrderLines(List.of(orderLineJPA));
        originalOrder.setTotalPrice(new BigDecimal("100.00"));

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(List.of(orderLineId));

        when(orderJPARepository.findById(eq(originalOrder.getId()))).thenReturn(Optional.of(originalOrder));
        when(orderJPARepository.idDateBeforeThirtyDays(eq(originalOrder.getId()), any(LocalDateTime.class))).thenReturn(false);
        when(orderJPARepository.save(any(OrderJPA.class))).thenReturn(originalOrder);

        BigDecimal result = orderServicesImpl.processReturnLines(originalOrder.getId(), returnLinesDTO);

        assertNotNull(result);

        assertEquals(new BigDecimal("100.00"), result);
        assertEquals(purchasedQuantity, orderLineJPA.getQuantity());
        assertEquals(purchasedQuantity, orderLineJPA.getReturnedQuantity());
        assertEquals(0, BigDecimal.ZERO.compareTo(originalOrder.getTotalPrice()));
        assertTrue(originalOrder.getOrderReturn());
        assertEquals(purchasedQuantity, originalOrder.getReturnedProductQuantity().get(productId));

        verify(orderJPARepository).save(originalOrder);
        verify(orderJPARepository).findById(originalOrder.getId());
        verify(orderJPARepository).idDateBeforeThirtyDays(eq(originalOrder.getId()), any(LocalDateTime.class));
    }

    private static OrderLineJPA getOrderLineJPA(UUID orderLineId, Long productId, int purchasedQuantity) {
        BigDecimal productPrice = new BigDecimal("10.00");

        OrderLineJPA orderLineJPA = new OrderLineJPA();
        orderLineJPA.setId(orderLineId);
        orderLineJPA.setProduct(productId);
        orderLineJPA.setQuantity(purchasedQuantity);
        orderLineJPA.setReturnedQuantity(0);
        orderLineJPA.setLinePrice(productPrice.multiply(BigDecimal.valueOf(purchasedQuantity)));
        return orderLineJPA;
    }

    @Test
    void shouldProcessMultipleReturnLinesSuccessfully() {

        UUID orderLineId1 = UUID.randomUUID();
        UUID orderLineId2 = UUID.randomUUID();
        Long productId1 = 101L;
        Long productId2 = 102L;
        int quantity1 = 5;
        int quantity2 = 3;
        BigDecimal price1 = new BigDecimal("20.00");
        BigDecimal price2 = new BigDecimal("5.00");

        OrderLineJPA line1 = new OrderLineJPA();
        line1.setId(orderLineId1);
        line1.setProduct(productId1);
        line1.setQuantity(quantity1);
        line1.setReturnedQuantity(0);
        line1.setLinePrice(price1.multiply(BigDecimal.valueOf(quantity1)));

        OrderLineJPA line2 = new OrderLineJPA();
        line2.setId(orderLineId2);
        line2.setProduct(productId2);
        line2.setQuantity(quantity2);
        line2.setReturnedQuantity(0);
        line2.setLinePrice(price2.multiply(BigDecimal.valueOf(quantity2)));

        originalOrder.setOrderLines(new ArrayList<>(Arrays.asList(line1, line2)));
        BigDecimal totalPrice = price1.multiply(BigDecimal.valueOf(quantity1))
                .add(price2.multiply(BigDecimal.valueOf(quantity2)));
        originalOrder.setTotalPrice(totalPrice);

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(Arrays.asList(orderLineId1, orderLineId2));

        when(orderJPARepository.findById(eq(originalOrder.getId()))).thenReturn(Optional.of(originalOrder));
        when(orderJPARepository.idDateBeforeThirtyDays(eq(originalOrder.getId()), any(LocalDateTime.class))).thenReturn(false);
        when(orderJPARepository.save(any(OrderJPA.class))).thenReturn(originalOrder); // Mock save

        BigDecimal result = orderServicesImpl.processReturnLines(originalOrder.getId(), returnLinesDTO);

        assertNotNull(result);
        assertEquals(totalPrice, result);

        assertEquals(quantity1, line1.getReturnedQuantity());
        assertEquals(quantity2, line2.getReturnedQuantity());

        assertEquals(0, BigDecimal.ZERO.compareTo(originalOrder.getTotalPrice()));
        assertTrue(originalOrder.getOrderReturn());
        assertEquals(quantity1, originalOrder.getReturnedProductQuantity().get(productId1));
        assertEquals(quantity2, originalOrder.getReturnedProductQuantity().get(productId2));

        verify(orderJPARepository).save(originalOrder);
    }



    @Test
    void shouldThrowInvalidReturnQuantityExceptionWhenOrderLineNotFound() {

        UUID nonExistentOrderLineId = UUID.randomUUID();
        originalOrder.setOrderLines(new ArrayList<>());

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(List.of(nonExistentOrderLineId));

        when(orderJPARepository.findById(eq(originalOrder.getId()))).thenReturn(Optional.of(originalOrder));

        InvalidReturnQuantityException exception = assertThrows(InvalidReturnQuantityException.class, () ->
                orderServicesImpl.processReturnLines(originalOrder.getId(), returnLinesDTO)
        );

        assertEquals("Order line with ID " + nonExistentOrderLineId + " not found in the specified order.", exception.getMessage());
        verify(orderJPARepository).findById(originalOrder.getId());
        verify(orderJPARepository, org.mockito.Mockito.never()).save(any(OrderJPA.class));
    }


    @Test
    void shouldThrowInvalidReturnQuantityExceptionWhenOrderLineAlreadyFullyReturned() {

        UUID orderLineId = UUID.randomUUID();
        Long productId = 123L;
        int purchasedQuantity = 10;

        OrderLineJPA orderLineJPA = new OrderLineJPA();
        orderLineJPA.setId(orderLineId);
        orderLineJPA.setProduct(productId);
        orderLineJPA.setQuantity(purchasedQuantity);
        orderLineJPA.setReturnedQuantity(purchasedQuantity);

        originalOrder.setOrderLines(new ArrayList<>(List.of(orderLineJPA)));

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(List.of(orderLineId));

        when(orderJPARepository.findById(eq(originalOrder.getId()))).thenReturn(Optional.of(originalOrder));

        InvalidReturnQuantityException exception = assertThrows(InvalidReturnQuantityException.class, () ->
                orderServicesImpl.processReturnLines(originalOrder.getId(), returnLinesDTO)
        );

        assertEquals("Order line with ID " + orderLineId + " has already been fully returned.", exception.getMessage());
        verify(orderJPARepository).findById(originalOrder.getId());
        verify(orderJPARepository, org.mockito.Mockito.never()).save(any(OrderJPA.class));
    }

    @Test
    void shouldThrowOrderNotFoundExceptionWhenOrderIsNotFound() {

        UUID nonExistentOrderId = UUID.randomUUID();
        when(orderJPARepository.findById(eq(nonExistentOrderId))).thenReturn(Optional.empty());

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(List.of(UUID.randomUUID()));

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () ->
                orderServicesImpl.processReturnLines(nonExistentOrderId, returnLinesDTO)
        );

        assertEquals("Original order not found", exception.getMessage());
        verify(orderJPARepository).findById(nonExistentOrderId);
        verify(orderJPARepository, org.mockito.Mockito.never()).save(any(OrderJPA.class));
    }

    @Test
    void shouldThrowReturnPeriodExceededExceptionWhenReturnPeriodIsExpired() {

        when(orderJPARepository.findById(eq(originalOrder.getId()))).thenReturn(Optional.of(originalOrder));
        when(orderJPARepository.idDateBeforeThirtyDays(eq(originalOrder.getId()), any(LocalDateTime.class))).thenReturn(true);

        ReturnLinesDTO returnLinesDTO = new ReturnLinesDTO(Collections.emptyList());

        ReturnPeriodExceededException exception = assertThrows(ReturnPeriodExceededException.class, () ->
                orderServicesImpl.processReturnLines(originalOrder.getId(), returnLinesDTO)
        );

        assertEquals("Return period exceeded", exception.getMessage());
        verify(orderJPARepository).findById(originalOrder.getId());
        verify(orderJPARepository).idDateBeforeThirtyDays(eq(originalOrder.getId()), any(LocalDateTime.class));
        verify(orderJPARepository, org.mockito.Mockito.never()).save(any(OrderJPA.class));
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
        verify(orderJPARepository).findAll();
        verify(mapper).toOrderModel(orderJPA1);
        verify(mapper).toOrderModel(orderJPA2);
    }

    @Test
    void createOrderReturn_success() {
        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));
        when(orderJPARepository.idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class))).thenReturn(false);
        when(orderJPARepository.save(any(OrderJPA.class))).thenReturn(originalOrder);

        BigDecimal result = orderServicesImpl.createOrderReturn(orderId);

        assertTrue(originalOrder.getOrderReturn());
        assertEquals(new BigDecimal("-100.00"), result); // Total price of originalOrder was 100.00

        verify(orderJPARepository).findById(orderId);
        verify(orderJPARepository).save(originalOrder);
        verify(orderJPARepository).idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class));
    }

    @Test
    void createOrderReturn_failure() {
        when(orderJPARepository.findById(orderId)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderServicesImpl.createOrderReturn(orderId));

        assertEquals("Original order not found", exception.getMessage());
        verify(orderJPARepository).findById(orderId);
    }

    @Test
    void createOrderReturn_ReturnPeriodExceeded(){
        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));
        when(orderJPARepository.idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class))).thenReturn(true);

        ReturnPeriodExceededException exception = assertThrows(ReturnPeriodExceededException.class, () -> orderServicesImpl.createOrderReturn(orderId));

        assertEquals("Return period exceeded", exception.getMessage());
        verify(orderJPARepository).findById(orderId);
        verify(orderJPARepository).idDateBeforeThirtyDays(eq(orderId), any(LocalDateTime.class));
    }

    @Test
    void createOrderReturn_InvalidStatusTransition(){
        originalOrder.setOrderReturn(true); // Mark as already returned
        when(orderJPARepository.findById(orderId)).thenReturn(Optional.of(originalOrder));

        InvalidOrderStatusTransitionException exception = assertThrows(InvalidOrderStatusTransitionException.class, () ->
                orderServicesImpl.createOrderReturn(orderId));

        assertEquals("A returned order cannot be reactivated.", exception.getMessage());
        verify(orderJPARepository).findById(orderId);
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
        originalOrder.setReturnedProductQuantity(new HashMap<>()); // Initialize the map for tests
    }
}