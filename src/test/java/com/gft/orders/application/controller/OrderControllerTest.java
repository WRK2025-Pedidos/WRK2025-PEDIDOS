package com.gft.orders.application.controller;

import com.gft.orders.domain.model.entity.Order;
import com.gft.orders.domain.model.entity.OrderReturn;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(value = OrderController.class)
class OrderControllerTest extends AbstractControllerTest {

}
