package com.gft.orders.unittest.offers;

import com.gft.orders.offer.OfferService;
import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.PromotionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
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

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime futureDate = now.plusDays(30);
        PromotionDTO seasonOffer = new PromotionDTO(1L, now, futureDate, 0.05, "SEASON", null, "food");

        PromotionDTO quantityOffer = new PromotionDTO(2L, now, futureDate, 0.10, "QUANTITY", 2, "toys");

        PromotionDTO quantityOfferInvalid = new PromotionDTO(3L, now, futureDate,0.15,"QUANTITY",5,"toys");

        PromotionDTO typeDefault = new PromotionDTO(1L,now,futureDate,0.03,"GENERIC_TYPE",null,"food");

        Map<String, List<PromotionDTO>> offersByCategory = Map.of(
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
