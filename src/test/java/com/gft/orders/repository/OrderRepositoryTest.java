package com.gft.orders.repository;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.entity.OrderReturn;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.domain.repository.OrderRepository;
import com.gft.orders.infraestructure.persistence.OrderEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts= {"/db/schema.sql"})
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void create_Test() {
        OrderEntity order = createTestOrder();
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

    /***********PRIVATE METHODS***********/
    private OrderEntity createTestOrder() {
        return Instancio.of(OrderEntity.class)
                .ignore(field(OrderEntity::getOffers))
                .supply(field(OrderEntity::getId), UUID::randomUUID)
                .supply(field(OrderEntity::getCartId), UUID::randomUUID)
                .supply(field(OrderEntity::getCreationDate), () -> LocalDateTime.now())
                .create();
    }

}
