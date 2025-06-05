package com.gft.orders.offer.client;

import com.gft.orders.offer.client.dto.CategoriesRequest;
import com.gft.orders.offer.client.dto.PromotionDTO;
import com.gft.orders.offer.client.dto.ProductDTO;
import com.gft.orders.offer.client.exception.ProductServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl = "https://workshop-7uvd.onrender.com/api/v1";

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<Long, String> getProductsCategories(Set<Long> productIds) {
        log.info("ProductServiceClient: Iniciando llamada a la API externa de productos para obtener categorias.");

        try{
            String url = productServiceUrl + "/products/list-by-ids";

            Map<Long, String> productsCategories = new HashMap<>();

            ResponseEntity<List<ProductDTO>> response =  restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(productIds),
                    new ParameterizedTypeReference<List<ProductDTO>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK ) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            if (response.getBody() == null) {
                return new HashMap<>();
            }

            for (ProductDTO productDTO : response.getBody()) {
                productsCategories.put(productDTO.id(), productDTO.category());
            }

            return productsCategories;

        }catch (RestClientException e){
            throw new ProductServiceException("Failed to retrieve product categories from external service: " + e.getMessage());
        }

    }

    public Map<String, List<PromotionDTO>> getOffersByCategories(Set<String> families) {

        log.info("ProductServiceClient: Iniciando llamada a la API externa de promociones.");

        try{
            String url = productServiceUrl + "/promotions/get-by-category";

            ResponseEntity<List<PromotionDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(new CategoriesRequest(families)),
                    new ParameterizedTypeReference<List<PromotionDTO>>() {}
            );

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ProductServiceException("Invalid response of Product Service");
            }

            if (response.getBody() == null) {
                log.warn("ProductService returned an empty body for promotions.");
                return new HashMap<>();
            }

            return response.getBody().stream()
                    .collect(Collectors.groupingBy(PromotionDTO::getCategory));

        }catch (RestClientException e){
            throw new ProductServiceException("Failed to retrieve offers by categories from external service: " + e.getMessage());
        }
    }
}
