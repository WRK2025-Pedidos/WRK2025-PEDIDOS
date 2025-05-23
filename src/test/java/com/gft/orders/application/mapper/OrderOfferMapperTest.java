package com.gft.orders.application.mapper;

import com.gft.orders.infraestructure.persistence.OrderJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferJPAEntity;
import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderOfferMapperTest {

    private OrderOfferMapperImpl orderOfferMapper;

    private OrderJPAEntity orderJPAEntity;
    private OrderOfferJPAEntity orderOfferJPAEntity;
    private UUID offerId;

    @BeforeEach
    void setUp() {
        orderOfferMapper = (OrderOfferMapperImpl) Mappers.getMapper(OrderOfferMapper.class);
        initData();
    }

    @Test
    void toEntityTest() {

        OrderOfferJPAEntity result = orderOfferMapper.toEntity(offerId, orderJPAEntity);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(offerId, result.getId().getOfferId());
        assertEquals(orderJPAEntity.getId(), result.getId().getOrderId());
        assertEquals(orderJPAEntity, result.getOrder());
    }

    @Test
    void toEntityWithNullOfferId() {

        OrderOfferJPAEntity result = orderOfferMapper.toEntity(null, orderJPAEntity);

        assertNotNull(result);
        assertNull(result.getId().getOfferId());
        assertEquals(orderJPAEntity.getId(), result.getId().getOrderId());
    }

    @Test
    void toEntityWithNullOrder() {

        OrderOfferJPAEntity result = orderOfferMapper.toEntity(offerId, null);

        assertNotNull(result);
        assertEquals(offerId, result.getId().getOfferId());
        assertNull(result.getId().getOrderId());
        assertNull(result.getOrder());
    }

    @Test
    void toOfferIdTest() {

        OrderOfferIdJPA id = new OrderOfferIdJPA();
        id.setOfferId(offerId);
        orderOfferJPAEntity.setId(id);

        UUID result = orderOfferMapper.toOfferId(orderOfferJPAEntity);

        assertEquals(offerId, result);
    }

    @Test
    void toOfferIdWithNullEntity() {

        UUID result = orderOfferMapper.toOfferId(null);

        assertNull(result);
    }

    @Test
    void toOfferIdWithNullId() {

        orderOfferJPAEntity.setId(null);

        UUID result = orderOfferMapper.toOfferId(orderOfferJPAEntity);

        assertNull(result);
    }

    @Test
    void toOfferIdWithNullOfferId() {

        OrderOfferIdJPA id = new OrderOfferIdJPA();
        id.setOfferId(null);
        orderOfferJPAEntity.setId(id);

        UUID result = orderOfferMapper.toOfferId(orderOfferJPAEntity);

        assertNull(result);
    }

    /****** Private Methods ******/
    private void initData() {
        offerId = UUID.randomUUID();

        orderJPAEntity = Instancio.create(OrderJPAEntity.class);
        orderJPAEntity.setId(UUID.randomUUID());

        orderOfferJPAEntity = Instancio.create(OrderOfferJPAEntity.class);
    }
}