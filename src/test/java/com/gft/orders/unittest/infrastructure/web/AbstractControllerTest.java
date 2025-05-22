package com.gft.orders.unittest.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void assertResponseBodyIsOk(MvcResult result, Object object) throws Exception {

        String responseBodyExpected = objectMapper.writeValueAsString(object);
        String responseBodyReceived = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(responseBodyReceived).isEqualToIgnoringWhitespace(responseBodyExpected);
    }
}