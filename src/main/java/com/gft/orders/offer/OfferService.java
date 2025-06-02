package com.gft.orders.offer;

import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.OfferDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OfferService {

    private final ProductServiceClient productServiceClient;

    public List<Long> getApplicableOffers(Map<Long, Integer> productQuantities) {

        Set<Long> productCategories = productServiceClient.getFamiliesCategories(productQuantities.keySet());

        Map<Long, List<OfferDto>> offersByCategory = productServiceClient.getOffersByFamilies(productCategories);

        return filterOffers(productQuantities, offersByCategory);
    }

    private List<Long> filterOffers(Map<Long, Integer> productQuantities, Map<Long, List<OfferDto>> offersByCategory) {

        List<Long> offers = new ArrayList<>();



    }
}
