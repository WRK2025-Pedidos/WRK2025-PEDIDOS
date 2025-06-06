package com.gft.orders.business.mapper;

import com.gft.orders.business.model.Order;
import com.gft.orders.business.model.OrderLine;
import com.gft.orders.business.model.OrderOffer;
import com.gft.orders.integration.model.OrderJPA;
import com.gft.orders.integration.model.OrderLineJPA;
import com.gft.orders.integration.model.OrderOfferJPA;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toOrderModel(OrderJPA orderJPA) {

        Order order = new Order();
        order.setId(orderJPA.getId());
        order.setUserId(orderJPA.getUserId());
        order.setCreationDate(orderJPA.getCreationDate());
        order.setTotalPrice(orderJPA.getTotalPrice());
        order.setCountryTax(orderJPA.getCountryTax());
        order.setPaymentMethod(orderJPA.getPaymentMethod());

            List<OrderLine> lines = orderJPA.getOrderLines().stream()
                    .map(this::toOrderLineModel)
                    .collect(Collectors.toList());
            order.setOrderLines(lines);

        order.setOrderReturn(orderJPA.getOrderReturn());

        return order;
    }

    public OrderJPA toOrderJPA(Order order) {

        OrderJPA orderJPA = new OrderJPA();
        orderJPA.setId(order.getId());
        orderJPA.setUserId(order.getUserId());
        orderJPA.setCreationDate(order.getCreationDate());
        orderJPA.setTotalPrice(order.getTotalPrice());
        orderJPA.setCountryTax(order.getCountryTax());
        orderJPA.setPaymentMethod(order.getPaymentMethod());

            List<OrderLineJPA> lines = order.getOrderLines().stream()
                    .map(line -> {
                        OrderLineJPA lineJPA = new OrderLineJPA();
                        lineJPA.setId(line.getId());
                        lineJPA.setProduct(line.getProduct());
                        lineJPA.setQuantity(line.getQuantity());
                        lineJPA.setLineWeight(line.getLineWeight());
                        lineJPA.setLinePrice(line.getLinePrice());
                        lineJPA.setOrder(orderJPA);
                        return lineJPA;
                    }).toList();

            orderJPA.setOrderLines(lines);

            orderJPA.setOrderReturn(order.getOrderReturn());

        return orderJPA;
    }

    public OrderLine toOrderLineModel(OrderLineJPA orderLineJPA) {

        return OrderLine.builder()
                .id(orderLineJPA.getId())
                .product(orderLineJPA.getProduct())
                .quantity(orderLineJPA.getQuantity())
                .lineWeight(orderLineJPA.getLineWeight())
                .linePrice(orderLineJPA.getLinePrice())
                .build();

    }

    public OrderLineJPA toOrderLineJPA(OrderLine orderLine) {

        OrderLineJPA lineJPA = new OrderLineJPA();
        lineJPA.setId(orderLine.getId());
        lineJPA.setProduct(orderLine.getProduct());
        lineJPA.setQuantity(orderLine.getQuantity());
        lineJPA.setLineWeight(orderLine.getLineWeight());
        lineJPA.setLinePrice(orderLine.getLinePrice());

        return lineJPA;
    }

    public OrderOfferJPA toOrderOfferJPA(OrderOffer orderOffer) {

        OrderOfferJPA orderOfferJPA = new OrderOfferJPA();
        orderOfferJPA.setOrderId(orderOffer.getOrderId());
        orderOfferJPA.setOfferId(orderOffer.getOfferId());

        return orderOfferJPA;
    }

    public OrderOffer toOrderOffer(OrderOfferJPA jpa) {

        return OrderOffer.builder()
                .orderId(jpa.getOrderId())
                .offerId(jpa.getOfferId())
                .build();
    }
}