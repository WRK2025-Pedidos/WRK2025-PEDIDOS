package com.gft.orders.integrationTest.Controller;

import com.gft.orders.business.config.exceptions.InvalidReturnQuantityException;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.presentation.controllers.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerIT extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderServices orderServices;

    private Order testOrder;
    private UUID testOrderId;

    @BeforeEach
    void setUp() {
        initData();
    }

    @Test
    void getAllOrders() throws Exception {

        List<Order> orders = Collections.singletonList(testOrder);
        when(orderServices.findAllOrders()).thenReturn(List.of(testOrder));

        MvcResult result = mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andReturn();

        assertResponseBodyIsOk(result, orders);

    }

    @Test
    void getOrderById_success() throws Exception {

        when(orderServices.findOrderById(testOrderId)).thenReturn(Optional.of(testOrder));

        MvcResult result = mockMvc.perform(get("/api/v1/orders/" + testOrderId))
                .andExpect(status().isOk())
                .andReturn();

        assertResponseBodyIsOk(result, testOrder);

    }

    @Test
    void getOrderById_failure() throws Exception {

        when(orderServices.findOrderById(testOrderId)).thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(get("/api/v1/orders/" + testOrderId))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody).contains("Order ID not found");
    }

    @Test
    void createOrder_success() throws Exception {

        when(orderServices.createOrder(any(Order.class))).thenReturn(testOrderId);

        String requestBody = objectMapper.writeValueAsString(testOrder);

        MvcResult result = mockMvc.perform(post("/api/v1/orders").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertResponseBodyIsOk(result, testOrderId);
    }

    @Test
    void createOrderReturn_success() throws Exception {

        when(orderServices.createOrderReturn(testOrderId)).thenReturn(BigDecimal.valueOf(-999.99));

        MvcResult result = mockMvc.perform(post("/api/v1/orders/" + testOrderId + "/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertResponseBodyIsOk(result, -999.99);
    }

    @Test
    void createOrderReturn_invalidQuantity() throws Exception {
        when(orderServices.createOrderReturn(testOrderId)).thenThrow(new InvalidReturnQuantityException("La cantidad a devolver no puede ser mayor que la cantidad comprada"));

        MvcResult result = mockMvc.perform(post("/api/v1/orders/" + testOrderId + "/return")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())  // Esperamos un error 400
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).contains("La cantidad a devolver no puede ser mayor que la cantidad comprada");
    }

    /**********PRIVATE METHODS*********/

    private void initData() {
        testOrderId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID testCartId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        OrderLine testOrderLine = new OrderLine(UUID.randomUUID(), 1L,1, 1.0, BigDecimal.valueOf(999.99),1);

        Map<Long, Integer> returnProductQuantity = new HashMap<>();
        returnProductQuantity.put(1L, 1);

        testOrder = new Order(testOrderId,
                        testCartId,
                        LocalDateTime.now(),
                        BigDecimal.valueOf(999.99),
                        21.0,
                        1.0,
                        List.of(testOrderLine),
                        List.of(),
                        false,
                        returnProductQuantity
        );
    }
}
