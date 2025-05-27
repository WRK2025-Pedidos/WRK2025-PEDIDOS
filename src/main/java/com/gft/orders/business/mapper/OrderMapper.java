package com.gft.orders.business.mapper;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toOrderModel(OrderJPA orderJPA) {
        if (orderJPA == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderJPA.getId());
        order.setCartId(orderJPA.getCartId());
        order.setCreationDate(orderJPA.getCreationDate());
        order.setTotalPrice(orderJPA.getTotalPrice());
        order.setCountryTax(orderJPA.getCountryTax());
        order.setPaymentMethod(orderJPA.getPaymentMethod());

        if (orderJPA.getOrderLines() != null) {
            List<OrderLine> lines = orderJPA.getOrderLines().stream()
                    .map(this::toOrderModel)
                    .collect(Collectors.toList());
            order.setOrderLines(lines);
        }

        return order;
    }

    public OrderJPA toOrderJPA(Order order) {
        if (order == null) {
            return null;
        }

        OrderJPA orderJPA = new OrderJPA();
        orderJPA.setId(order.getId());
        orderJPA.setCartId(order.getCartId());
        orderJPA.setCreationDate(order.getCreationDate());
        orderJPA.setTotalPrice(order.getTotalPrice());
        orderJPA.setCountryTax(order.getCountryTax());
        orderJPA.setPaymentMethod(order.getPaymentMethod());

        List<OrderLineJPA> lines = order.getOrderLines().stream()
                .map(line -> {
                    OrderLineJPA lineJPA = new OrderLineJPA();
                    lineJPA.setProduct(line.getProduct());
                    lineJPA.setQuantity(line.getQuantity());
                    lineJPA.setLineWeight(line.getLineWeight());
                    lineJPA.setProductPrice(line.getProductPrice());
                    lineJPA.setLinePrice(line.getLinePrice());
                    lineJPA.setOrder(orderJPA);
                    return lineJPA;
                }).toList();

        orderJPA.setOrderLines(lines);


        return orderJPA;
    }

    public OrderLine toOrderModel(OrderLineJPA orderLineJPA) {
        if (orderLineJPA == null) {
            return null;
        }

        return OrderLine.builder()
                .product(orderLineJPA.getProduct())
                .quantity(orderLineJPA.getQuantity())
                .lineWeight(orderLineJPA.getLineWeight())
                .productPrice(orderLineJPA.getProductPrice())
                .linePrice(orderLineJPA.getLinePrice())
                .build();
    }

    public OrderLineJPA toOrderJPA(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }

        OrderLineJPA lineJPA = new OrderLineJPA();

        lineJPA.setProduct(orderLine.getProduct());
        lineJPA.setQuantity(orderLine.getQuantity());
        lineJPA.setLineWeight(orderLine.getLineWeight());
        lineJPA.setProductPrice(orderLine.getProductPrice());
        lineJPA.setLinePrice(orderLine.getLinePrice());

        return lineJPA;
    }
}