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

        Map<String, Integer> productQuantitiesInput = Map.of("1", 1, "2", 2);

        Map<Long, Integer> productQuantitiesExpectedByService = Map.of(1L, 1, 2L, 2);

        List<Long> expectedOffers = List.of(10L, 20L);

        when(offerService.getApplicableOffers(productQuantitiesExpectedByService))
                .thenReturn(expectedOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantitiesInput);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOffers, response.getBody());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);

    }

    @Test
    void getApplicableOffers_shouldHandleEmptyInput(){

        Map<String, Integer> emptyInput = Map.of();

        Map<Long, Integer> emptyInputForService = Map.of();

        List<Long> emptyResponse = List.of();

        when(offerService.getApplicableOffers(emptyInputForService))
                .thenReturn(emptyResponse);

        ResponseEntity<List<Long>> response =
                offerController.getApplicableOffers(emptyInput);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(emptyInputForService);
    }

    @Test
    void getApplicableOffers_shouldReturnEmptyListWhenNoOffers(){

        Map<String, Integer> productQuantitiesInput = Map.of("1", 1);

        Map<Long, Integer> productQuantitiesExpectedByService = Map.of(1L, 1);

        List<Long> emptyOffers = List.of();

        when(offerService.getApplicableOffers(productQuantitiesExpectedByService))
                .thenReturn(emptyOffers);

        ResponseEntity<List<Long>> response = offerController.getApplicableOffers(productQuantitiesInput);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(offerService).getApplicableOffers(productQuantitiesExpectedByService);
    }

}
