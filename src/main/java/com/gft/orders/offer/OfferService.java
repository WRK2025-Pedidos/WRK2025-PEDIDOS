package com.gft.orders.offer;

import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.PromotionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OfferService {

    private final ProductServiceClient productServiceClient;

    public OfferService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public List<Long> getApplicableOffers(Map<Long, Integer> productQuantities) {

        log.info("Iniciando lógica de negocio para obtener ofertas aplicables.");
        log.debug("Cantidades de productos recibidas en OfferService: {}", productQuantities);

        Map<Long, String> productsCategories = productServiceClient.getProductsCategories(productQuantities.keySet());

        Set<String> uniqueCategories = new HashSet<>(productsCategories.values());

        Map<String, List<PromotionDTO>> offersByCategory = productServiceClient.getOffersByCategories(uniqueCategories);

        return filterOffers(productQuantities, offersByCategory);
    }

    private List<Long> filterOffers(Map<Long, Integer> productQuantities, Map<String, List<PromotionDTO>> offersByCategory) {

        List<Long> applicableOffers = new ArrayList<>();

        Map<Long, String> productsCategories = productServiceClient.getProductsCategories(productQuantities.keySet());

        Map<String, Integer> quantityPerCategory = new HashMap<>();
        productQuantities.forEach((productId, quantity) -> {
            String categoryId = productsCategories.get(productId);
            quantityPerCategory.merge(categoryId, quantity, Integer::sum);
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

    private boolean isOfferApplicable(PromotionDTO promotion, int totalQuantityInCategory) {

        return switch (promotion.getPromotionType().toUpperCase()) {
            case "SEASON" -> true;
            case "QUANTITY" -> totalQuantityInCategory >= promotion.getQuantity();
            default -> false;
        };
    }
}
