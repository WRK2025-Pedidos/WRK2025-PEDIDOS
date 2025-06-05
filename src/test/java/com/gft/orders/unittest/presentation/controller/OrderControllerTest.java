package com.gft.orders.unittest.presentation.controller;

import com.gft.orders.business.config.exceptions.OrderNotFoundException;
import com.gft.orders.business.config.exceptions.ReturnPeriodExceededException;
import com.gft.orders.integration.repositories.OrderJPARepository;
import com.gft.orders.presentation.config.ExceptionsGeneralHandler;
import com.gft.orders.presentation.controllers.OrderController;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.business.model.Order;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(value = OrderController.class)
@Import(ExceptionsGeneralHandler.class)
class OrderControllerTest extends AbstractControllerTest {
    @MockitoBean
    private OrderServices orderServices;

    Order order1;
    Order order2;

    private OrderJPARepository orderJPARepository;

    @BeforeEach
    void init() {
        order1 = Instancio.create(Order.class);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        Order order = Instancio.create(Order.class);
        when(orderServices.createOrder(order)).thenReturn(orderId);
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + orderId + "\""));
    }

    @Test
    void findAllOrders_Test() throws Exception {
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderServices.findAllOrders()).thenReturn(orders);
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findOrderById_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        order1.setId(orderId);
        when(orderServices.findOrderById(orderId)).thenReturn(Optional.of(order1));
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/orders/" + orderId))
                .andExpect(status().isOk())
                .andReturn();
        assertResponseBodyIsOk(mvcResult, order1);
    }

    @Test
    void findOrderById_NotFound_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(orderServices.findOrderById(orderId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/orders/" + orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Order ID not found")));
    }
}

