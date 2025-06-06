package com.gft.orders.unittest.integration.repository;

import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts= {"/db/schema.sql"})
public class OrderJPARepositoryTest {

    @Autowired
    private OrderJPARepository orderJPARepository;

    @BeforeEach
    public void setUp() {
        orderJPARepository.deleteAll();
    }

    @Test
    public void create_Test() {
        OrderJPA order = createTestOrder();
        OrderJPA saved = orderJPARepository.save(order);
        assertNotNull(saved.getId());
    }

    @Test
    public void findById_Test() {
        OrderJPA order = createTestOrder();
        orderJPARepository.save(order);
        Optional<OrderJPA> result = orderJPARepository.findById(order.getId());
        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());
    }

    @Test
    public void update_Test() {
        OrderJPA order = createTestOrder();
        order.setTotalPrice(BigDecimal.valueOf(990.00));
        orderJPARepository.save(order);

        order.setTotalPrice(BigDecimal.valueOf(888.00));
        OrderJPA updated = orderJPARepository.save(order);
        assertEquals(BigDecimal.valueOf(888.00), updated.getTotalPrice());
    }

    /***********PRIVATE METHODS***********/

    private OrderJPA createTestOrder() {
        OrderJPA order = new OrderJPA();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.randomUUID());

        OrderLineJPA line = new OrderLineJPA();
        line.setProduct(5L);
        line.setQuantity(1);
        line.setLineWeight(0.5);
        line.setLinePrice(BigDecimal.TEN);

        order.setOrderLines(List.of(line));
        order.setCreationDate(LocalDateTime.now());
        order.setCountryTax(0.2);
        order.setPaymentMethod(0.3);
        order.setTotalPrice(BigDecimal.TEN);
        order.setOrderReturn(false);
        order.setOrderOffers(List.of());

        return order;
    }
}