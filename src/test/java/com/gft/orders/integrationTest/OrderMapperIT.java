package com.gft.orders.integrationTest;

import com.gft.orders.business.mapper.OrderMapper;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class OrderMapperIT {

    @Autowired
    private OrderMapper orderMapper;

    private Order order;
    private OrderLine line1;
    private OrderJPA orderJPA;
    private OrderLineJPA line1JPA;

    @BeforeEach
    void setUp() {
        initData();
    }

    @Test
    void contextLoads() {
        assertNotNull(orderMapper);
    }

    @Test
    void mapperBean_shouldMapOrder() {
        UUID id = UUID.randomUUID();
        orderJPA.setId(id);

        Order order = orderMapper.toOrderModel(orderJPA);

        assertNotNull(order);
        assertEquals(orderJPA.getId(), order.getId());
    }

    @Test
    void mapperBean_shouldMapOrderBack() {
        UUID id = UUID.randomUUID();
        order.setId(id);

        OrderJPA orderJPA = orderMapper.toOrderJPA(order);

        assertNotNull(orderJPA);
        assertEquals(order.getId(), orderJPA.getId());
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
        line1JPA.setProduct(2L);
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
                .product(3L)
                .quantity(3)
                .lineWeight(2.0)
                .productPrice(BigDecimal.valueOf(50.25))
                .linePrice(BigDecimal.valueOf(150.75))
                .build();

        order.setOrderLines(List.of(line1));
    }
}

