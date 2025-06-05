package com.gft.orders.unittest.offers;

import com.gft.orders.offer.client.ProductServiceClient;
import com.gft.orders.offer.client.dto.OfferDTO;
import com.gft.orders.offer.client.dto.ProductDTO;
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

import java.util.HashMap;
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

    private final String baseUrl = "https://workshop-7uvd.onrender.com/api/v1";

    @Test
    void getProductsCategories_success() {

        Set<Long> productIds = Set.of(1L, 2L);
        List<ProductDTO> mockProductResponse = List.of(
                new ProductDTO(1L, "food"),
                new ProductDTO(2L, "toys")
        );

        when(restTemplate.exchange(
                baseUrl + "/products/list-by-ids",
                HttpMethod.POST,
                new HttpEntity<>(productIds),
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        )).thenReturn(new ResponseEntity<>(mockProductResponse, HttpStatus.OK));

        Map<Long, String> expectedResultFromMethod = Map.of(1L, "food", 2L, "toys");

        Map<Long, String> result = productServiceClient.getProductsCategories(productIds);

        assertEquals(expectedResultFromMethod, result);
        verify(restTemplate).exchange(
                eq(baseUrl + "/products/list-by-ids"),
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

        Exception exception = assertThrows(ProductServiceException.class, () -> productServiceClient.getProductsCategories(Set.of(1L)));

        assertTrue(exception.getMessage().contains("Invalid response of Product Service"));

    }

    @Test
    void getProductsCategories_WhenBodyIsNull_ReturnsEmptyMap() {
        Set<Long> productIds = Set.of(1L);

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        Map<Long, String> result = productServiceClient.getProductsCategories(productIds);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(new HashMap<>(), result);
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

        Set<String> categoryIds = Set.of("food", "toys");
        Map<String, List<OfferDTO>> expectedResponse = Map.of(
                "food", List.of(new OfferDTO(1L, "SEASON", null, null)),
                "toys", List.of(new OfferDTO(2L, "QUANTITY", null, 3))
                );

        when(restTemplate.exchange(
                eq(baseUrl + "/promotions/get-by-category"),
                eq(HttpMethod.POST),
                argThat(httpEntity -> {
                    if (httpEntity.getBody() instanceof CategoriesRequest) {
                        CategoriesRequest requestBody = (CategoriesRequest) httpEntity.getBody();
                        return requestBody.categories().equals(categoryIds);
                    }
                    return false;
                }),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        Map<String, List<OfferDTO>> result = productServiceClient.getOffersByCategories(categoryIds);

        assertEquals(2, result.size());
        assertEquals(1, result.get("food").size());

        verify(restTemplate).exchange(
                eq(baseUrl + "/promotions/get-by-category"),
                eq(HttpMethod.POST),
                argThat(httpEntity -> {
                    if (httpEntity.getBody() instanceof CategoriesRequest) {
                        CategoriesRequest requestBody = (CategoriesRequest) httpEntity.getBody();
                        return requestBody.categories().equals(categoryIds);
                    }
                    return false;
                }),
                any(ParameterizedTypeReference.class));
    }

    @Test
    void getOffersByCategories_WhenEmptyResponse_ThrowsException(){

        when(restTemplate.exchange(
                anyString(),
                any(),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThrows(ProductServiceException.class, () -> {
            productServiceClient.getOffersByCategories(Set.of("1L"));
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

        Exception exception = assertThrows(ProductServiceException.class, () -> productServiceClient.getOffersByCategories(Set.of("1L")));

        assertTrue(exception.getMessage().contains("Failed to retrieve offers by categories from external service"));
        assertTrue(exception.getMessage().contains(error));
    }
}
