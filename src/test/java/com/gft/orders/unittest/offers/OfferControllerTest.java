package com.gft.orders.unittest.offers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gft.orders.offer.OfferController;
import com.gft.orders.offer.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    @Test
    void getApplicableOffers_shouldReturnListOfOffersIds() throws JsonProcessingException {

        Map<Long, Integer> productQuantitiesInput = Map.of(1L, 1, 2L, 2);

        List<Long> expectedOffers = List.of(10L, 20L);

        when(offerService.getApplicableOffers(productQuantitiesInput))
                .thenReturn(expectedOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantitiesInput);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());

        verify(offerService).getApplicableOffers(productQuantitiesInput);

    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput() throws JsonProcessingException {

        Map<Long, Integer> emptyProductQuantities = Collections.emptyMap();

        List<Long> emptyResponse = List.of();

        when(offerService.getApplicableOffers(emptyProductQuantities))
                .thenReturn(emptyResponse);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(emptyProductQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(emptyProductQuantities);
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers() {

        Map<Long, Integer> singleItemProductQuantities = Map.of(1L, 1);

        List<Long> emptyOffers = List.of();

        when(offerService.getApplicableOffers(singleItemProductQuantities))
                .thenReturn(emptyOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(singleItemProductQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(singleItemProductQuantities);
    }
}