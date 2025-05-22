package com.gft.orders.unittest.domain.service;

import com.gft.orders.application.service.impl.OrderReturnServicesImpl;
import com.gft.orders.domain.model.entity.OrderReturn;
import com.gft.orders.domain.repository.OrderReturnRepository;
import com.gft.orders.infraestructure.persistence.OrderReturnJPAEntity;
import org.dozer.DozerBeanMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderReturnServicesImplTest {

    @Mock
    private DozerBeanMapper mapper;

    @Mock
    private OrderReturnRepository orderReturnRepository;

    @InjectMocks
    private OrderReturnServicesImpl orderReturnServicesImpl;

    private OrderReturn orderReturn;
    private OrderReturnJPAEntity orderReturnJPAEntity;

    @BeforeEach
    public void setUp() {
        initObjects();
    }

    @Test
    void shouldFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderReturnRepository.findById(uuid)).thenReturn(Optional.of(orderReturnJPAEntity));
        when(mapper.map(orderReturnJPAEntity, OrderReturn.class)).thenReturn(orderReturn);

        Optional<OrderReturn> optional = orderReturnServicesImpl.findOrderReturnById(uuid);

        assertTrue(optional.isPresent());
        assertEquals(orderReturn, optional.get());
    }

    @Test
    void shouldNotFindOrder() {

        UUID uuid = UUID.randomUUID();

        when(orderReturnRepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<OrderReturn> optional = orderReturnServicesImpl.findOrderReturnById(uuid);

        assertTrue(optional.isEmpty());
    }

    /***************PRIVATE METHODS***********/
    private void initObjects() {

        orderReturn = Instancio.create(OrderReturn.class);
        orderReturnJPAEntity = Instancio.create(OrderReturnJPAEntity.class);
    }
}
