package com.gft.orders.integrationtest;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.service.OrderServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class LogAspectIT {

    @Autowired
    private OrderServices orderServices;

    @Test
    void shouldLogWhenCallingCreateOrder(CapturedOutput output) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setTotalPrice(BigDecimal.TEN);

        order.setOrderLines(new ArrayList<>());

        UUID orderId = orderServices.createOrder(order);

        assertThat(orderId).isNotNull();

        assertThat(output.getOut()).contains("Iniciando método: OrderServiceImpl.createOrder");
        assertThat(output.getOut()).contains("Método finalizado: OrderServiceImpl.createOrder");
        assertThat(output.getOut()).contains("Método completado: OrderServiceImpl.createOrder");
    }
}
