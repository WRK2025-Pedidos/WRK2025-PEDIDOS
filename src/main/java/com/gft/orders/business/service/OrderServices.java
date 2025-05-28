package com.gft.orders.business.service;

import com.gft.orders.business.model.DTO.ReturnLineDTO;
import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

    UUID createOrder(Order order);

    Optional<Order> findOrderById(UUID id);

    List<Order> findAllOrders();

    UUID createOrderReturn(UUID orderId, Map<UUID, ReturnLineDTO> orderReturnLines);

    BigDecimal calculateLineTotal(OrderLine orderLine);

    BigDecimal calculateLinesTotal(List<OrderLine> orderLines);

    boolean enoughProductQuantity(UUID orderLine, BigDecimal quantity);

    BigDecimal calculateReturnTotal(BigDecimal totalOrderLines, double countryTax, double paymentTax);
}
