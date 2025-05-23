package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderLineRequest;
import com.gft.orders.application.dto.OrderRequest;
import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @Mock
    private OrderOfferMapper offerMapper;

    @Mock
    private OrderLineMapper orderLineMapper;

    @InjectMocks
    private OrderMapperImpl orderMapper;

    private OrderRequest orderRequest;
    private Order domain;
    private OrderLineRequest orderLineRequest;
    private OrderLine orderLine;
    private OrderJPAEntity orderJPAEntity;
    private OrderOfferJPAEntity orderOfferJPAEntity;

    @BeforeEach
    void setUp() {
        initData();
    }

    @Test
    void toDomainFromRequestTest() {
        when(orderLineMapper.toDomain(any(OrderLineRequest.class)))
                .thenReturn(orderLine);

        Order result = orderMapper.toDomain(orderRequest);

        assertNotNull(result);
        verify(orderLineMapper, times(orderRequest.getOrderLines().size()))
                .toDomain(any(OrderLineRequest.class));
    }

    @Test
    void toResponseFromDomainTest() {
        UUID offerId = UUID.randomUUID();
        domain.setOffers(List.of(offerId));

        OrderResponse response = orderMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1, response.offers().size());
        assertEquals(offerId, response.offers().get(0));
    }

    @Test
    void toEntityWithOffersTest() {
        UUID offerId = UUID.randomUUID();
        domain.setOffers(List.of(offerId));

        when(offerMapper.toEntity(any(UUID.class), any(OrderJPAEntity.class)))
                .thenReturn(orderOfferJPAEntity);
        when(orderLineMapper.toEntity(any(OrderLine.class)))
                .thenReturn(new OrderLineJPAEntity());

        OrderJPAEntity entity = orderMapper.toEntity(domain);
        orderMapper.mapOffers(entity, domain, offerMapper);

        assertNotNull(entity);
        assertNotNull(entity.getOffers());
        assertEquals(1, entity.getOffers().size());
    }

    @Test
    void toEntityWithNullOffers() {
        domain.setOffers(null);

        OrderJPAEntity entity = orderMapper.toEntity(domain);
        orderMapper.mapOffers(entity, domain, offerMapper);

        assertNotNull(entity);
        assertNull(entity.getOffers());
        verifyNoInteractions(offerMapper);
    }

    @Test
    void toDomainFromEntityTest() {

        OrderOfferIdJPA orderOfferId = new OrderOfferIdJPA();
        orderOfferId.setOfferId(UUID.randomUUID());
        orderOfferId.setOrderId(UUID.randomUUID());

        OrderOfferJPAEntity offerEntity = new OrderOfferJPAEntity();
        offerEntity.setId(orderOfferId);

        orderJPAEntity.setOffers(List.of(offerEntity));

        Order result = orderMapper.toDomain(orderJPAEntity);

        assertNotNull(result);
        assertNotNull(result.getOffers());
        assertEquals(1, result.getOffers().size());
    }

    @Test
    void toDomainFromEntityWithNullOffers() {
        orderJPAEntity.setOffers(null);

        Order result = orderMapper.toDomain(orderJPAEntity);

        assertNotNull(result);
        assertNull(result.getOffers());
    }

    private void initData() {
        orderRequest = Instancio.create(OrderRequest.class);
        domain = Instancio.create(Order.class);
        orderLineRequest = Instancio.create(OrderLineRequest.class);
        orderLine = Instancio.create(OrderLine.class);
        orderJPAEntity = Instancio.create(OrderJPAEntity.class);
        orderOfferJPAEntity = Instancio.create(OrderOfferJPAEntity.class);

        orderRequest.setOrderLines(List.of(orderLineRequest));
        domain.setOrderLines(List.of(orderLine));
    }
}