package com.gft.orders.unittest.business.model.DTO;

import com.gft.orders.business.model.DTO.ReturnLinesDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnLinesDTOTest {

    @Test
    void createReturnLinesDTOTest() {

        UUID orderLineId1 = UUID.randomUUID();
        UUID orderLineId2 = UUID.randomUUID();
        List<UUID> lineIdsToReturn = Arrays.asList(orderLineId1, orderLineId2);

        ReturnLinesDTO dto = new ReturnLinesDTO(lineIdsToReturn);

        assertNotNull(dto);
        assertEquals(2, dto.returnLines().size());
        assertTrue(dto.returnLines().contains(orderLineId1));
        assertTrue(dto.returnLines().contains(orderLineId2));
        assertEquals(lineIdsToReturn, dto.returnLines());
    }

    @Test
    void createReturnLinesDTO_emptyListTest() {

        List<UUID> emptyList = List.of();

        ReturnLinesDTO dto = new ReturnLinesDTO(emptyList);

        assertNotNull(dto);
        assertTrue(dto.returnLines().isEmpty());
    }
}
