package com.gft.orders.repository;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.domain.repository.OrderRepository;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void create_Test() {
        Order order = new Order();
        Order saved = orderRepository.save(order);
        assertNotNull(saved.getId());
    }

    @Test
    public void findAll_Test() {
       orderRepository.save(createTestOrder());
       orderRepository.save(createTestOrder());
       orderRepository.save(createTestOrder());

       List<Order> orders = orderRepository.findAll();
       assertEquals(3, orders.size());
    }

    @Test
    public void findById_Test() {
      Order order = createTestOrder();
      orderRepository.save(order);
      Optional<Order> result = orderRepository.findById(order.getId());
      assertTrue(result.isPresent());
      assertEquals(order.getId(), result.get().getId());

    }

    @Test
    public void update_Test() {
        Order order = createTestOrder();
        order.setTotalPrice(BigDecimal.valueOf(990.00));
        orderRepository.save(order);

        order.setTotalPrice(BigDecimal.valueOf(888.00));
        Order updated = orderRepository.save(order);
        assertEquals(BigDecimal.valueOf(888.00), updated.getTotalPrice());
    }

    @Test
    public void modifyStockProduct_Test() {
        Order order = createTestOrder();
        UUID productId = order.getOrderLines().get(0).getProduct();
        order = orderRepository.save(order);

        for (OrderLine line : order.getOrderLines()) {
            if (line.getProduct().equals(productId)) {
                line.setQuantity(10);
            }
        }

        Order updated = orderRepository.save(order);
        assertEquals(10, updated.getOrderLines().get(0).getQuantity());
    }

    @Test
    public void reviewPromotion_Test() {
        Order order = createTestOrder();
        UUID offerId =UUID.randomUUID();
        order.setOffers(List.of(offerId));
        order = orderRepository.save(order);

        assertFalse(order.getOffers().isEmpty());
    }

    private Order createTestOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCartId(UUID.randomUUID());
        order.setTotalPrice(BigDecimal.valueOf(150.00));
        order.setCountryTax(0.21);
        order.setPaymentMethod(1.0);
        order.setCreationDate(LocalDate.now());

        OrderLine line1 = new OrderLine();
        line1.setProduct(UUID.randomUUID());
        line1.setQuantity(2);
        line1.setLineWeight(1.5);
        line1.setLinePrice(BigDecimal.valueOf(75.00));

        order.setOrderLines(List.of(line1));
        order.setOffers(new ArrayList<>());

        return order;
    }
}
