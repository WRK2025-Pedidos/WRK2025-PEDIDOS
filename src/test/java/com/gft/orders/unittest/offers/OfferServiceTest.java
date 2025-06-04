package com.gft.orders.unittest.offers;

import com.gft.orders.offer.OfferService;
import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.OfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private OfferService offerService;

    @Test
    void getApplicableOffers_shouldReturnFilteredOffers() {

        Map<Long, Integer> productQuantities = Map.of(1L, 2, 2L, 2);
        Map<Long, Long> productCategories = Map.of(1L, 10L, 2L, 20L);
        Set<Long> uniqueCategories = Set.of(10L, 20L);

        OfferDTO seasonOffer = new OfferDTO(1L, "SEASON", 10L, null);
        OfferDTO quantityOffer = new OfferDTO(2L, "QUANTITY", 20L, 2);
        OfferDTO quantityOfferInvalid = new OfferDTO(3L, "QUANTITY", 20L, 5);
        OfferDTO typeDefault = new OfferDTO(1L, "X", 10L, null);

        Map<Long, List<OfferDTO>> offersByCategory = Map.of(
                10L, List.of(seasonOffer, typeDefault),
                20L, List.of(quantityOffer, quantityOfferInvalid)
        );

        when(productServiceClient.getProductsCategories(anySet()))
                .thenReturn(productCategories);

        when(productServiceClient.getOffersByCategories(anySet()))
                .thenReturn(offersByCategory);

        List<Long> result = offerService.getApplicableOffers(productQuantities);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(1L, 2L)));
        verify(productServiceClient, times(2)).getProductsCategories(anySet());
        verify(productServiceClient).getOffersByCategories(uniqueCategories);

    }
}
