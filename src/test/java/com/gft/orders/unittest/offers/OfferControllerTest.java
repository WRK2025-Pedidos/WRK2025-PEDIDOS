package com.gft.orders.unittest.offers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.orders.offer.OfferController;
import com.gft.orders.offer.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OfferController offerController;

    private static final String SAMPLE_JSON_INPUT = "{\"1\":1,\"2\":2}";
    private static final String EMPTY_JSON_INPUT = "{}";
    private static final String SINGLE_ITEM_JSON_INPUT = "{\"1\":1}";

<<<<<<< Updated upstream
        Map<Long, Integer> productQuantities = Map.of(1L, 1, 2L, 2);
=======
    @BeforeEach
    void setUp() throws JsonProcessingException {

        Map<String, Integer> sampleMap = Map.of("1", 1, "2", 2);
        lenient().when(objectMapper.readValue(eq(SAMPLE_JSON_INPUT), any(TypeReference.class)))
                .thenReturn(sampleMap);

        Map<String, Integer> emptyMap = Map.of();
        lenient().when(objectMapper.readValue(eq(EMPTY_JSON_INPUT), any(TypeReference.class)))
                .thenReturn(emptyMap);

        Map<String, Integer> singleItemMap = Map.of("1", 1);
        lenient().when(objectMapper.readValue(eq(SINGLE_ITEM_JSON_INPUT), any(TypeReference.class)))
                .thenReturn(singleItemMap);
    }


    @Test
    void getApplicableOffers_shouldReturnListOfOffersIds() throws JsonProcessingException {

        Map<Long, Integer> productQuantitiesExpectedByService = Map.of(1L, 1, 2L, 2);

>>>>>>> Stashed changes
        List<Long> expectedOffers = List.of(10L, 20L);

        when(offerService.getApplicableOffers(anyMap()))
                .thenReturn(expectedOffers);

<<<<<<< Updated upstream
        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());
        verify(offerService).getApplicableOffers(productQuantities);
    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput(){

        Map<Long, Integer> emptyInput = Map.of();
=======
        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(SAMPLE_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);

        verify(objectMapper).readValue(eq(SAMPLE_JSON_INPUT), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput() throws JsonProcessingException {

        Map<Long, Integer> emptyInputForService = Map.of();

>>>>>>> Stashed changes
        List<Long> emptyResponse = List.of();

        when(offerService.getApplicableOffers(emptyInput))
                .thenReturn(emptyResponse);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(EMPTY_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
<<<<<<< Updated upstream
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers(){

        Map<Long, Integer> productQuantities = Map.of(1L, 1);
=======

        verify(offerService).getApplicableOffers(emptyInputForService);

        verify(objectMapper).readValue(eq(EMPTY_JSON_INPUT), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers() throws JsonProcessingException {

        Map<Long, Integer> productQuantitiesExpectedByService = Map.of(1L, 1);

>>>>>>> Stashed changes
        List<Long> emptyOffers = List.of();

        when(offerService.getApplicableOffers(productQuantities))
                .thenReturn(emptyOffers);

<<<<<<< Updated upstream
        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
=======
        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(SINGLE_ITEM_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);

        verify(objectMapper).readValue(eq(SINGLE_ITEM_JSON_INPUT), any(TypeReference.class));
    }


>>>>>>> Stashed changes
}
