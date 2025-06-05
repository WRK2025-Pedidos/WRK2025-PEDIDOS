package com.gft.orders.offer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
<<<<<<< Updated upstream
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestParam Map<Long, Integer> productQuantities) {

        List<Long> offerIds = offerService.getApplicableOffers(productQuantities);
=======
    public ResponseEntity<List<Long>> getApplicableOffers(@RequestParam("productQuantitiesJson") String productQuantitiesJson) throws JsonProcessingException {

        Map<String, Integer> stringKeyQuantities;

            stringKeyQuantities = objectMapper.readValue(productQuantitiesJson, new TypeReference<Map<String, Integer>>() {});


        Map<Long, Integer> actualProductQuantities = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stringKeyQuantities.entrySet()) {

                actualProductQuantities.put(Long.parseLong(entry.getKey()), entry.getValue());

        }

        List<Long> offerIds = offerService.getApplicableOffers(actualProductQuantities);
>>>>>>> Stashed changes

        return ResponseEntity.ok(offerIds);
    }
}
