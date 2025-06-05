package com.gft.orders.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/offers")
@Slf4j
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestBody Map<Long, Integer> productQuantities) {

        log.info("Petición POST recibida en /api/v1/offers.");
        log.debug("Cantidad de productos recibida: {}", productQuantities);

        List<Long> offerIds = offerService.getApplicableOffers(productQuantities);

        return ResponseEntity.ok(offerIds);
    }
}
