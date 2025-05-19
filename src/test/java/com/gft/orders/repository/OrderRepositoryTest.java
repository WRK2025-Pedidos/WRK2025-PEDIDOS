package com.gft.orders.repository;

import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts= {"/db/schema.sql"})
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void create_Test() {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID());
        OrderEntity saved = orderRepository.save(order);
        assertNotNull(saved.getId());
    }

    @Test
    public void findAll_Test() {
       orderRepository.save(createTestOrder());
       orderRepository.save(createTestOrder());
       orderRepository.save(createTestOrder());

       List<OrderEntity> orders = orderRepository.findAll();
       assertEquals(3, orders.size());
    }

    @Test
    public void findById_Test() {
      OrderEntity order = createTestOrder();
      orderRepository.save(order);
      Optional<OrderEntity> result = orderRepository.findById(order.getId());
      assertTrue(result.isPresent());
      assertEquals(order.getId(), result.get().getId());

    }

    @Test
    public void update_Test() {
        OrderEntity order = createTestOrder();
        order.setTotalPrice(BigDecimal.valueOf(990.00));
        orderRepository.save(order);

        order.setTotalPrice(BigDecimal.valueOf(888.00));
        OrderEntity updated = orderRepository.save(order);
        assertEquals(BigDecimal.valueOf(888.00), updated.getTotalPrice());
    }

    private OrderEntity createTestOrder() {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID());
        order.setCartId(UUID.randomUUID());
        order.setTotalPrice(BigDecimal.valueOf(150.00));
        order.setCountryTax(0.21);
        order.setPaymentMethod(1.0);
        order.setCreationDate(LocalDateTime.now());

        OrderLine line1 = new OrderLine();
        line1.setProduct(UUID.randomUUID());
        line1.setQuantity(2);
        line1.setLineWeight(1.5);
        line1.setLinePrice(BigDecimal.valueOf(75.00));

        order.setOrderLine(List.of(line1));
        order.setOffers(new ArrayList<>());

        return order;
    }
}
