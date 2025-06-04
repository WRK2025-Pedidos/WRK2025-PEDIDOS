package com.gft.orders.offer.client;

import com.gft.orders.offer.client.dto.OfferDTO;
import com.gft.orders.offer.client.exception.ProductServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl = "http://productsUrl"; //TODO actualizar cuando producto la tenga disponible

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<Long, Long> getProductsCategories(Set<Long> productIds) {
        try{
            String url = productServiceUrl + "/productsCategories"; // TODO actualizar cuando productos tenga el endpoint disponible

            ResponseEntity<Map<Long, Long>> response =  restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(productIds),
                    new ParameterizedTypeReference<Map<Long, Long>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK ) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            return response.getBody();

        }catch (RestClientException e){
            throw new ProductServiceException("Failed to retrieve product categories from external service: " + e.getMessage());
        }

    }

    public Map<Long, List<OfferDTO>> getOffersByCategories(Set<Long> familyIds) {

        try{
            String url = productServiceUrl + "/offersByCategories"; // TODO actualizar cuando productos tenga el endpoint disponible

            ResponseEntity<Map<Long, List<OfferDTO>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(familyIds),
                    new ParameterizedTypeReference<Map<Long, List<OfferDTO>>>() {}
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            return response.getBody();

        }catch (RestClientException e){
            throw new ProductServiceException("Failed to retrieve offers by categories from external service: " + e.getMessage());
        }
    }
}
