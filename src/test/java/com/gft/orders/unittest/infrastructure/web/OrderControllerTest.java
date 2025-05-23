package com.gft.orders.unittest.infrastructure.web;

import com.gft.orders.application.controller.OrderController;
import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.application.service.OrderServices;
import com.gft.orders.domain.model.Order;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(value = OrderController.class)
class OrderControllerTest extends AbstractControllerTest {

    @MockitoBean
    private OrderServices orderServices;

    Order order1;
    Order order2;

    @BeforeEach
    void init() {
        order1 = Instancio.create(Order.class);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder_Test() throws Exception {
        UUID orderId = UUID.randomUUID();

        OrderDTO orderDTO = Instancio.create(OrderDTO.class);

        when(orderServices.createOrder(orderDTO)).thenReturn(orderId);

        String requestBody = objectMapper.writeValueAsString(orderDTO);

        mockMvc.perform(post("/orders")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","http://localhost/orders/" + orderId));
    }

    @Test
    void findAllOrders_Test() throws Exception {
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderServices.findAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void notFindAllOrders_Test() throws Exception {
        mockMvc.perform(get("/orders").param("view", "INVALID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findOrderById_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        order1.setId(orderId);

        when(orderServices.findOrderById(orderId)).thenReturn(Optional.of(order1));

        MvcResult mvcResult = mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isOk())
                .andReturn();

        assertResponseBodyIsOk(mvcResult, order1);
    }

    @Test
    void findOrderById_NotFound_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        order1.setId(orderId);

        when(orderServices.findOrderById(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isNotFound());
    }

}