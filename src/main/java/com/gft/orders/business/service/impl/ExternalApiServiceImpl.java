package com.gft.orders.business.service.impl;

import com.gft.orders.business.service.ExternalApiService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Generated
public class ExternalApiServiceImpl implements ExternalApiService {

    private final RestTemplate restTemplate;
    private final String externalApiBaseUrl;

    public ExternalApiServiceImpl(RestTemplate restTemplate, @Value("${external.api.base.url}") String externalApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.externalApiBaseUrl = externalApiBaseUrl;
    }

    @Override
    public String getDataFromExternalApi() {
        return "Calling external API from: " + externalApiBaseUrl;
    }
}
