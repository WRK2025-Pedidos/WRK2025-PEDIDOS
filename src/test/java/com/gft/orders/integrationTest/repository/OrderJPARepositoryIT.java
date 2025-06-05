package com.gft.orders.integrationTest.repository;

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
    void idDateBeforeThirtyDaysTest_success(){


        Boolean response = repository.idDateBeforeThirtyDays(id, LocalDateTime.now().minusDays(30));

        assertEquals(Boolean.TRUE, response);

    }

    @Test
    void idDateBeforeThirtyDaysTest_fail(){

        Optional<OrderJPA> order = repository.findById(id);
        order.get().setCreationDate(LocalDateTime.now().minusDays(40));

        repository.save(order.get());

        Boolean response = repository.idDateBeforeThirtyDays(id, LocalDateTime.now());

        assertEquals(Boolean.FALSE, response);

    }
}
