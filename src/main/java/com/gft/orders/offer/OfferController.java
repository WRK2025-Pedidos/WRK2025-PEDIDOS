package com.gft.orders.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestParam Map<String, Integer> productQuantities) {

        Map<Long, Integer> productQuantitiesMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                productQuantitiesMap.put(Long.parseLong(entry.getKey()), entry.getValue());
        }

        List<Long> offerIds = offerService.getApplicableOffers(productQuantitiesMap);

        return ResponseEntity.ok(offerIds);
    }
}
