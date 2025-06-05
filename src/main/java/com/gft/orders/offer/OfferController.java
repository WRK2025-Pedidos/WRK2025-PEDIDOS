package com.gft.orders.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestParam Map<Long, Integer> productQuantities) {

        List<Long> offerIds = offerService.getApplicableOffers(productQuantities);

        return ResponseEntity.ok(offerIds);

    }
}
