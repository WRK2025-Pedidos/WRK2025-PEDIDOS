package com.gft.orders.repository;

import com.gft.orders.domain.repository.OrderReturnRepository;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
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
@Sql(scripts = {"/db/schema.sql"})
public class OrderReturnRepositoryTest {

    @Autowired
    private OrderReturnRepository orderReturnRepository;

    @Test
    public void findById_shouldReturnOrderReturnJPAEntity() {
        OrderReturnJPAEntity entity = createTestOrderReturn();
        orderReturnRepository.save(entity);

        Optional<OrderReturnJPAEntity> result = orderReturnRepository.findById(entity.getId());

        assertTrue(result.isPresent());
        assertEquals(entity.getId(), result.get().getId());
    }

    @Test
    public void findById_shouldReturnEmptyWhenNotFound() {
        Optional<OrderReturnJPAEntity> result = orderReturnRepository.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    /*********** PRIVATE METHODS ***********/
    private OrderReturnJPAEntity createTestOrderReturn() {
        OrderReturnJPAEntity orderReturn = new OrderReturnJPAEntity();
        orderReturn.setId(UUID.randomUUID());
        orderReturn.setOrderId(UUID.randomUUID());
        orderReturn.setTotalPrice(BigDecimal.TEN);
        orderReturn.setCountryTax(0.2);
        orderReturn.setPaymentMethod(0.3);
        orderReturn.setCreationDate(LocalDateTime.now());

        OrderLineJPAEntity line = new OrderLineJPAEntity();
        line.setProduct(UUID.randomUUID());
        line.setQuantity(1);
        line.setLineWeight(0.5);
        line.setProductPrice(BigDecimal.TEN);
        line.setLinePrice(BigDecimal.TEN);

        orderReturn.setOrderLines(List.of(line));
        orderReturn.setOffers(List.of());

        return orderReturn;
    }
}
