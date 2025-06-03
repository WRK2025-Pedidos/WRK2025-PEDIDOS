package com.gft.orders.offer.client;

import com.gft.orders.offer.client.dto.OfferDto;
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
    private final String productServiceUrl = "http://urlProductos"; //TODO actualizar cuando producto la tenga disponible

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

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            return response.getBody();

        }catch (RestClientException e){
            throw new ProductServiceException("Error: "+ e.getMessage());
        }

    }

    public Map<Long, List<OfferDto>> getOffersByCategories(Set<Long> familyIds) {

        try{
            String url = productServiceUrl + "/offersByCategories"; // TODO actualizar cuando productos tenga el endpoint disponible

            ResponseEntity<Map<Long, List<OfferDto>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(familyIds),
                    new ParameterizedTypeReference<Map<Long, List<OfferDto>>>() {}
            );

            if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            return response.getBody();

        }catch (RestClientException e){
            throw new ProductServiceException("Error: "+ e.getMessage());
        }
    }
}
