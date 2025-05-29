package com.gft.orders.unittest.business.model.DTO;

import com.gft.orders.business.model.DTO.ReturnLineDTO;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnLineDTOTest {

    @Test
    void createReturnLineDTOTest() {

        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Map<UUID, Integer> returnLines = Map.of(productId, 5);

        ReturnLineDTO dto = new ReturnLineDTO(orderId, returnLines);

        assertEquals(orderId, dto.original_order());
        assertEquals(returnLines,dto.returnLines());
        assertEquals(5, dto.returnLines().get(productId));

    }
}
