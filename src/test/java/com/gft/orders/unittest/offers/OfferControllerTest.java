package com.gft.orders.unittest.offers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        List<Long> expectedOffers = List.of(10L, 20L);

        when(offerService.getApplicableOffers(productQuantitiesExpectedByService))
                .thenReturn(expectedOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(SAMPLE_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);
        verify(objectMapper).readValue(eq(SAMPLE_JSON_INPUT), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput() throws JsonProcessingException {

        Map<Long, Integer> emptyInputForService = Map.of();

        List<Long> emptyResponse = List.of();

        when(offerService.getApplicableOffers(emptyInputForService))
                .thenReturn(emptyResponse);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(EMPTY_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(emptyInputForService);
        verify(objectMapper).readValue(eq(EMPTY_JSON_INPUT), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers() throws JsonProcessingException {

        Map<Long, Integer> productQuantitiesExpectedByService = Map.of(1L, 1);

        List<Long> emptyOffers = List.of();

        when(offerService.getApplicableOffers(productQuantitiesExpectedByService))
                .thenReturn(emptyOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(SINGLE_ITEM_JSON_INPUT);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);
        verify(objectMapper).readValue(eq(SINGLE_ITEM_JSON_INPUT), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldHandleInvalidJsonFormat() throws JsonProcessingException {

        String invalidJsonInput = "{invalid json}";

        when(objectMapper.readValue(eq(invalidJsonInput), any(TypeReference.class)))
                .thenThrow(new JsonProcessingException("Invalid JSON") {});

        assertThrows(IllegalArgumentException.class, () -> {
            offerController.getApplicableOffers(invalidJsonInput);
        });

        verify(offerService, never()).getApplicableOffers(anyMap());

        verify(objectMapper).readValue(eq(invalidJsonInput), any(TypeReference.class));
    }

    @Test
    void getApplicableOffers_shouldHandleInvalidKeyFormatInJson() throws JsonProcessingException {

        String jsonWithInvalidKey = "{\"abc\":1}";
        Map<String, Integer> mapWithInvalidKey = Map.of("abc", 1);

        when(objectMapper.readValue(eq(jsonWithInvalidKey), any(TypeReference.class)))
                .thenReturn(mapWithInvalidKey);

        assertThrows(IllegalArgumentException.class, () -> {
            offerController.getApplicableOffers(jsonWithInvalidKey);
        });

        verify(offerService, never()).getApplicableOffers(anyMap());

        verify(objectMapper).readValue(eq(jsonWithInvalidKey), any(TypeReference.class));
    }

}
