package com.gft.orders.application.mapper;

import com.gft.orders.application.dto.OrderLineRequest;
import com.gft.orders.application.dto.OrderLineResponse;
import com.gft.orders.domain.model.valueObject.OrderLine;
import com.gft.orders.infraestructure.persistence.OrderLineJPAEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLineMapperTest {

    OrderLineMapper orderLineMapper;

    private OrderLineRequest orderLineRequest;
    private OrderLine orderLine;
    private OrderLineJPAEntity orderLineJPAEntity;

    @BeforeEach
    public void setUp() {
        orderLineMapper = Mappers.getMapper(OrderLineMapper.class);
        initData();
    }
    @Test
    void toDomainFromRequestTest(){

        OrderLine result = orderLineMapper.toDomain(orderLineRequest);

        assertNotNull(result);
        assertEquals(orderLineRequest.getProduct(), result.getProduct());
        assertEquals(orderLineRequest.getQuantity(), result.getQuantity());
        assertEquals(orderLineRequest.getLinePrice(), result.getLinePrice());
    }

    @Test
    void toResponseFromDomainTest(){

        OrderLineResponse result = orderLineMapper.toResponse(orderLine);

        assertNotNull(result);
        assertEquals(orderLine.getProduct(), result.product());
        assertEquals(orderLine.getQuantity(), result.quantity());
        assertEquals(orderLine.getLinePrice(), result.linePrice());

    }

    @Test
    void toEntityFromDomainTest(){

        OrderLineJPAEntity result = orderLineMapper.toEntity(orderLine);

        assertNotNull(result);
        assertEquals(orderLine.getProduct(), result.getProduct());
        assertEquals(orderLine.getQuantity(), result.getQuantity());
        assertEquals(orderLine.getLinePrice(), result.getLinePrice());

    }

    @Test
    void toDomainFromEntityTest() {
        OrderLine result = orderLineMapper.toDomain(orderLineJPAEntity);

        assertNotNull(result);
        assertEquals(orderLineJPAEntity.getProduct(), result.getProduct());
        assertEquals(orderLineJPAEntity.getQuantity(), result.getQuantity());
        assertEquals(orderLineJPAEntity.getLinePrice(), result.getLinePrice());
    }

    @Test
    void toDomainFromNullRequest() {
        OrderLine result = orderLineMapper.toDomain((OrderLineRequest) null);
        assertNull(result);
    }

    @Test
    void toResponseFromNullDomain() {
        OrderLineResponse response = orderLineMapper.toResponse((OrderLine) null);
        assertNull(response);
    }

    @Test
    void toEntityFromNullDomain() {
        OrderLineJPAEntity entity = orderLineMapper.toEntity((OrderLine) null);
        assertNull(entity);
    }

    @Test
    void toDomainFromNullEntity() {
        OrderLine result = orderLineMapper.toDomain((OrderLineJPAEntity) null);
        assertNull(result);
    }

    /****** Private Methods ******/
    private void initData() {

        orderLineRequest = Instancio.create(OrderLineRequest.class);
        orderLine = Instancio.create(OrderLine.class);
        orderLineJPAEntity = Instancio.create(OrderLineJPAEntity.class);
    }
}
