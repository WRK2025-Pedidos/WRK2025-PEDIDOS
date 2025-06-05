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
        Map<Long, String> productCategories = Map.of(1L, "food", 2L, "toys");
        Set<String> uniqueCategories = Set.of("food", "toys");

        OfferDTO seasonOffer = new OfferDTO(1L, "SEASON", "food", null);
        OfferDTO quantityOffer = new OfferDTO(2L, "QUANTITY", "toys", 2);
        OfferDTO quantityOfferInvalid = new OfferDTO(3L, "QUANTITY", "toys", 5);
        OfferDTO typeDefault = new OfferDTO(1L, "X", "food", null);

        Map<String, List<OfferDTO>> offersByCategory = Map.of(
                "food", List.of(seasonOffer, typeDefault),
                "toys", List.of(quantityOffer, quantityOfferInvalid)
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
