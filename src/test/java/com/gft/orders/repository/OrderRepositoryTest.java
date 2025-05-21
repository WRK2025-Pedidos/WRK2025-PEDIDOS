package com.gft.orders.repository;

import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    public void create_Test() {
        OrderJPAEntity order = createTestOrder();
        OrderJPAEntity saved = orderRepository.save(order);
        assertNotNull(saved.getId());
    }

    @Test
    public void findAll_Test() {
       orderRepository.save(createTestOrder());

       List<OrderJPAEntity> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
    }

    @Test
    public void findById_Test() {
      OrderJPAEntity order = createTestOrder();
      orderRepository.save(order);
      Optional<OrderJPAEntity> result = orderRepository.findById(order.getId());
      assertTrue(result.isPresent());
      assertEquals(order.getId(), result.get().getId());
    }

    @Test
    public void update_Test() {
        OrderJPAEntity order = createTestOrder();
        order.setTotalPrice(BigDecimal.valueOf(990.00));
        orderRepository.save(order);

        order.setTotalPrice(BigDecimal.valueOf(888.00));
        OrderJPAEntity updated = orderRepository.save(order);
        assertEquals(BigDecimal.valueOf(888.00), updated.getTotalPrice());
    }

    /***********PRIVATE METHODS***********/

    private OrderJPAEntity createTestOrder() {
        OrderJPAEntity order = new OrderJPAEntity();
        order.setId(UUID.randomUUID());
        order.setCartId(UUID.randomUUID());

        OrderLineJPAEntity line = new OrderLineJPAEntity();
        line.setProduct(UUID.randomUUID());
        line.setQuantity(1);
        line.setLineWeight(0.5);
        line.setProductPrice(BigDecimal.TEN);
        line.setLinePrice(BigDecimal.TEN);

        order.setOrderLines(List.of(line));
        order.setCreationDate(LocalDateTime.now());
        order.setCountryTax(0.2);
        order.setPaymentMethod(0.3);
        order.setTotalPrice(BigDecimal.TEN);

        return order;
    }
}
