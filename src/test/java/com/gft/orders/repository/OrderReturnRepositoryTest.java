package com.gft.orders.repository;

import com.gft.orders.domain.repository.OrderReturnRepository;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OrderReturnRepositoryTest {

    @Autowired
    private OrderReturnRepository orderReturnRepository;

    @Test
    void addReturnLine_shouldAddLineAndSetOrderReturn() {
        OrderReturnJPAEntity orderReturn = new OrderReturnJPAEntity();
        orderReturn.setId(UUID.randomUUID());

        OrderLineJPAEntity orderLine = new OrderLineJPAEntity();
        orderLine.setId(UUID.randomUUID());

        orderReturn.addReturnLine(orderLine);

        assertThat(orderReturn.getReturnLines()).contains(orderLine);

        assertThat(orderLine.getOrderReturn()).isEqualTo(orderReturn);
    }
}
