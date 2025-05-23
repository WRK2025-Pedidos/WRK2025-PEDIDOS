package com.gft.orders.application.mapper;

import com.gft.orders.infraestructure.persistence.OrderOfferIdJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderOfferIdMapperTest {

    private OrderOfferIdMapper mapper;

    private UUID orderId;
    private UUID offerId;

    @BeforeEach
    public void setUp() {

        mapper = Mappers.getMapper(OrderOfferIdMapper.class);
        initData();
    }

    @Test
    void toEntityTest(){

        OrderOfferIdJPA result = mapper.toEntity(orderId, offerId);

        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertEquals(offerId, result.getOfferId());
    }

    /****** Private Methods ******/
    private void initData() {
        orderId = UUID.randomUUID();
        offerId = UUID.randomUUID();
    }
}
