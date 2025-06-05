package com.gft.orders.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestBody Map<Long, Integer> productQuantities) {

        List<Long> offerIds = offerService.getApplicableOffers(productQuantities);

        return ResponseEntity.ok(offerIds);
    }
}
