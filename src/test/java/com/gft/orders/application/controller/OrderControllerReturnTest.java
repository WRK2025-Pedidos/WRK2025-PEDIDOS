package com.gft.orders.application.controller;

import com.gft.orders.application.service.OrderReturnServices;
import com.gft.orders.domain.model.entity.OrderReturn;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderReturnController.class)
class OrderControllerReturnTest extends AbstractControllerTest {

    @MockitoBean
    private OrderReturnServices orderReturnServices;

    OrderReturn orderReturn;

    @BeforeEach
    void init(){
        orderReturn = Instancio.create(OrderReturn.class);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findOrderById_Test() throws Exception {
        UUID orderId = UUID.randomUUID();
        orderReturn.setOrderId(orderId);

        when(orderReturnServices.findOrderReturnById(orderId)).thenReturn(Optional.of(orderReturn));

        MvcResult mvcResult = mockMvc.perform(get("/orders_return/" + orderId))
                .andExpect(status().isOk())
                .andReturn();

        assertResponseBodyIsOk(mvcResult, orderReturn);
    }
}
