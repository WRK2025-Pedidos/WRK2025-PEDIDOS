package com.gft.orders.unittest.business.mapper;

import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.business.model.OrderOffer;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import com.gft.orders.integration.model.OrderOfferJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {

    private OrderMapper orderMapper;

    private Order order;
    private OrderLine line1;
    private OrderJPA orderJPA;
    private OrderLineJPA line1JPA;
    private OrderOffer orderOffer;
    private OrderOfferJPA orderOfferJPA;

    @BeforeEach
    void setup() {
        orderMapper = new OrderMapper();
        orderOffer = new OrderOffer();
        initData();
    }

    @Test
    void toOrderModel_shouldMap_Test() {

        Order result = orderMapper.toOrderModel(orderJPA);

        assertNotNull(result);
        assertEquals(orderJPA.getId(), result.getId());
        assertEquals(orderJPA.getTotalPrice(), result.getTotalPrice());
        assertEquals(orderJPA.getUserId(), result.getUserId());
        assertEquals(orderJPA.getCountryTax(), result.getCountryTax());
        assertEquals(orderJPA.getPaymentMethod(), result.getPaymentMethod());
        assertEquals(orderJPA.getCreationDate(), result.getCreationDate());

        assertNotNull(result.getOrderLines());

        OrderLine resultLine = result.getOrderLines().get(0);
        assertEquals(line1JPA.getLinePrice(), resultLine.getLinePrice());
        assertEquals(line1JPA.getLineWeight(), resultLine.getLineWeight());
        assertEquals(line1JPA.getQuantity(), resultLine.getQuantity());
        assertEquals(line1JPA.getProductPrice(), resultLine.getProductPrice());

    }

    @Test
    void toOrderJPA_shouldMap_Test() {

        OrderJPA result = orderMapper.toOrderJPA(order);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getCountryTax(), result.getCountryTax());
        assertEquals(order.getPaymentMethod(), result.getPaymentMethod());
        assertEquals(order.getCreationDate(), result.getCreationDate());

        assertNotNull(result.getOrderLines());

        OrderLineJPA resultLine = result.getOrderLines().get(0);
        assertEquals(line1.getLinePrice(), resultLine.getLinePrice());
        assertEquals(line1.getLineWeight(), resultLine.getLineWeight());
        assertEquals(line1.getQuantity(), resultLine.getQuantity());
        assertEquals(line1.getProductPrice(), resultLine.getProductPrice());

        }

    @Test
    void toOrderLineModel_shouldMap_Test() {

        OrderLine result = orderMapper.toOrderLineModel(line1JPA);

        assertNotNull(result);
        assertEquals(line1JPA.getLinePrice(), result.getLinePrice());
        assertEquals(line1JPA.getLineWeight(), result.getLineWeight());
        assertEquals(line1JPA.getQuantity(), result.getQuantity());
        assertEquals(line1JPA.getProductPrice(), result.getProductPrice());

    }

    @Test
    void toOrderLineJPA_shouldMap_Test() {

        OrderLineJPA result = orderMapper.toOrderLineJPA(line1);

        assertNotNull(result);
        assertEquals(line1.getLinePrice(), result.getLinePrice());
        assertEquals(line1.getLineWeight(), result.getLineWeight());
        assertEquals(line1.getQuantity(), result.getQuantity());
        assertEquals(line1.getProductPrice(), result.getProductPrice());
    }

    @Test
    void toOrderOfferJPA_shouldMap_Test() {
        OrderOfferJPA result = orderMapper.toOrderOfferJPA(orderOffer);

        assertNotNull(result);
        assertEquals(orderOffer.getOrderId(), result.getOrderId());
        assertEquals(orderOffer.getOfferId(), result.getOfferId());
    }

    @Test
    void toOrderOffer_shouldMap_Test() {
        OrderOffer result = orderMapper.toOrderOffer(orderOfferJPA);

        assertNotNull(result);
        assertEquals(orderOfferJPA.getOrderId(), result.getOrderId());
        assertEquals(orderOfferJPA.getOfferId(), result.getOfferId());
    }


    /***********PRIVATE METHODS**********/

    private void initData() {
        orderJPA = new OrderJPA();
        orderJPA.setId(UUID.randomUUID());
        orderJPA.setUserId(UUID.randomUUID());
        orderJPA.setCreationDate(LocalDateTime.now());
        orderJPA.setTotalPrice(BigDecimal.valueOf(100.50));
        orderJPA.setCountryTax(21.0);
        orderJPA.setPaymentMethod(1.0);

        line1JPA = new OrderLineJPA();
        line1JPA.setProduct(5L);
        line1JPA.setQuantity(2);
        line1JPA.setLineWeight(1.5);
        line1JPA.setProductPrice(BigDecimal.valueOf(50.25));
        line1JPA.setLinePrice(BigDecimal.valueOf(100.50));

        orderJPA.setOrderLines(List.of(line1JPA));


        order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.randomUUID());
        order.setCreationDate(LocalDateTime.now());
        order.setTotalPrice(BigDecimal.valueOf(150.75));
        order.setCountryTax(21.0);
        order.setPaymentMethod(2.0);

        line1 = OrderLine.builder()
                .product(1L)
                .quantity(3)
                .lineWeight(2.0)
                .productPrice(BigDecimal.valueOf(50.25))
                .linePrice(BigDecimal.valueOf(150.75))
                .build();

        order.setOrderLines(List.of(line1));

        orderOffer = OrderOffer.builder()
                .orderId(UUID.randomUUID())
                .offerId(orderOffer.getOfferId())
                .build();

        orderOfferJPA = new OrderOfferJPA();
        orderOfferJPA.setOrderId(orderOffer.getOrderId());
        orderOfferJPA.setOfferId(orderOffer.getOfferId());
    }
}
