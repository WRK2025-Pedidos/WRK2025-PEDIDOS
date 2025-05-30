package com.gft.orders.integrationtest.repository;

import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.repositories.OrderJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(scripts = {"/db/data.sql", "/db/schema.sql"})
public class OrderJPARepositoryIT {

    @Autowired
    OrderJPARepository repository;

    private final UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Test
    void findOrderByIdTest(){

        Optional<OrderJPA> order = repository.findById(id);

        assertThat(order.isPresent());
        assertThat(order.get().getId()).isEqualTo(id);

    }

    @Test
    void idDateBeforeThirtyDaysTest(){


        Boolean response = repository.idDateBeforeThirtyDays(id, LocalDateTime.now());

        assertEquals(Boolean.TRUE, response);

    }
}
