package com.gft.orders.offer;

import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final ProductServiceClient productServiceClient;

    public OfferService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public record ProductCategoryResponse(Long productId, Long categoryId) {}

    public List<Long> getApplicableOffers(Map<Long, Integer> productQuantities) {

        Map<Long, Long> productsCategories = productServiceClient.getProductsCategories(productQuantities.keySet());

        Set<Long> uniqueCategories = new HashSet<>(productsCategories.values());

        Map<Long, List<OfferDto>> offersByCategory = productServiceClient.getOffersByCategories(uniqueCategories);

        return filterOffers(productQuantities, offersByCategory);
    }

    private List<Long> filterOffers(Map<Long, Integer> productQuantities, Map<Long, List<OfferDto>> offersByCategory) {

        List<Long> applicableOffers = new ArrayList<>();

        Map<Long, Long> productsCategories = productServiceClient.getProductsCategories(productQuantities.keySet());

        Map<Long, Integer> quantityPerCategory = new HashMap<>();
        productQuantities.forEach((productId, quantitiy) -> {
            Long categoryId = productsCategories.get(productId);
            quantityPerCategory.merge(categoryId, quantitiy, Integer::sum);
        });

        offersByCategory.forEach((categoryId, offers) -> {
            int totalQuantityInCategory = quantityPerCategory.getOrDefault(categoryId, 0);

            offers.forEach(offer -> {

                if(isOfferApplicable(offer, totalQuantityInCategory)) {
                    applicableOffers.add(offer.getId());
                }
            });
        });

        return applicableOffers;
    }


    private boolean isOfferApplicable(OfferDto offer, int totalQuantityInCategory) {

        return switch (offer.getType().toUpperCase()) {
            case "SEASON" -> true;
            case "QUANTITY" -> totalQuantityInCategory >= offer.getQuantity();
            default -> false;
        };
    }
}
