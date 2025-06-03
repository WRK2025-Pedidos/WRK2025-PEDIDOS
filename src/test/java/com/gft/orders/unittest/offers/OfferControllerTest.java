package com.gft.orders.unittest.offers;

import com.gft.orders.offer.OfferController;
import com.gft.orders.offer.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    @Test
    void getApplicableOffers_shouldReturnListOfOffersIds(){

        Map<Long, Integer> productQuantities = Map.of(1L, 1, 2L, 2);
        List<Long> expectedOffers = List.of(10L, 20L);

        when(offerService.getApplicableOffers(anyMap()))
                .thenReturn(expectedOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());
        verify(offerService).getApplicableOffers(productQuantities);
    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput(){

        Map<Long, Integer> emptyInput = Map.of();
        List<Long> emptyResponse = List.of();

        when(offerService.getApplicableOffers(emptyInput))
                .thenReturn(emptyResponse);

        ResponseEntity<List<Long>> response =
                offerController.getApplicableOffers(emptyInput);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers(){

        Map<Long, Integer> productQuantities = Map.of(1L, 1);
        List<Long> emptyOffers = List.of();

        when(offerService.getApplicableOffers(productQuantities))
                .thenReturn(emptyOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantities);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
