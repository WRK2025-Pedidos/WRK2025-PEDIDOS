package com.gft.orders.unittest.offers;

import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.OfferDTO;
import com.gft.orders.offer.client.exception.ProductServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceClient productServiceClient;

    private final String baseUrl = "http://productsUrl";

    @Test
    void getProductsCategories_success() {

        Set<Long> productIds = Set.of(1L, 2L);
        Map<Long, Long> expectedResponse = Map.of(1L, 10L, 2L, 20L);

        when(restTemplate.exchange(
                baseUrl + "/productsCategories",
                HttpMethod.POST,
                new HttpEntity<>(productIds),
                new ParameterizedTypeReference<Map<Long, Long>>() {}
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));


        Map<Long, Long> result = productServiceClient.getProductsCategories(productIds);

        assertEquals(expectedResponse, result);
        verify(restTemplate).exchange(
                eq(baseUrl + "/productsCategories"),
                eq(HttpMethod.POST),
                argThat(entity -> ((Set<?>) entity.getBody()).containsAll(productIds)),
                any(ParameterizedTypeReference.class));

    }

    @Test
    void getProductsCategories_WhenServiceReturnsError_ThrowsException() {

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ProductServiceException.class, () -> productServiceClient.getProductsCategories(Set.of(1L)));
    }

    @Test
    void getProductsCategories_WhenRestClientException_ThrowsException() {

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new ProductServiceException("Connection error"));

        Exception exception = assertThrows(ProductServiceException.class, () -> productServiceClient.getProductsCategories(Set.of(1L)));

        assertTrue(exception.getMessage().contains("Connection error"));

    }

    @Test
    void getProductsCategories_WhenRestClientExceptionOccurs_ThrowsException() {

        String errorMessage = "Connection refused: localhost/127.0.0.1:8080";

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException(errorMessage));

        Exception exception = assertThrows(ProductServiceException.class, () -> productServiceClient.getProductsCategories(Set.of(1L)));

        assertTrue(exception.getMessage().contains("Failed to retrieve product categories from external service"));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
    @Test
    void getOffersByCategories_success(){

        Set<Long> categoryIds = Set.of(10L,20L);
        Map<Long, List<OfferDTO>> expectedResponse = Map.of(
                10L, List.of(new OfferDTO(1L, "SEASON", null, null)),
                20L, List.of(new OfferDTO(2L, "QUANTITY", null, 3))
                );

        when(restTemplate.exchange(
                baseUrl + "/offersByCategories",
                HttpMethod.POST,
                new HttpEntity<>(categoryIds),
                new ParameterizedTypeReference<Map<Long, List<OfferDTO>>>() {}
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        Map<Long, List<OfferDTO>> result = productServiceClient.getOffersByCategories(categoryIds);

        assertEquals(2, result.size());
        assertEquals(1, result.get(10L).size());

        verify(restTemplate).exchange(
                eq(baseUrl + "/offersByCategories"),
                eq(HttpMethod.POST),
                argThat(entity -> ((Set<?>) entity.getBody()).containsAll(categoryIds)),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getOffersByCategories_WhenEmptyResponse_ThrowsException(){

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        assertThrows(ProductServiceException.class, () -> {
            productServiceClient.getOffersByCategories(Set.of(1L));
        });
    }

    @Test
    void getOffersByCategories_WhenRestClientException_ThrowsException() {

        String error = "Read timed out";

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException(error));

        Exception exception = assertThrows(ProductServiceException.class, () -> productServiceClient.getOffersByCategories(Set.of(1L)));

        assertTrue(exception.getMessage().contains("Failed to retrieve offers by categories from external service"));
        assertTrue(exception.getMessage().contains(error));
    }
}
