package com.gft.orders.offer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;
    private final ObjectMapper objectMapper;

    public OfferController(OfferService offerService, ObjectMapper objectMapper) {
        this.offerService = offerService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestParam("productQuantitiesJson") String productQuantitiesJson) {

        Map<String, Integer> stringKeyQuantities;
        try {
            stringKeyQuantities = objectMapper.readValue(productQuantitiesJson, new TypeReference<Map<String, Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON format for productQuantities: " + e.getMessage(), e);
        }

        Map<Long, Integer> actualProductQuantities = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stringKeyQuantities.entrySet()) {
            try {
                actualProductQuantities.put(Long.parseLong(entry.getKey()), entry.getValue());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid product ID (key) format in JSON: " + entry.getKey(), e);
            }
        }

        List<Long> offerIds = offerService.getApplicableOffers(actualProductQuantities);

        return ResponseEntity.ok(offerIds);
    }
}
