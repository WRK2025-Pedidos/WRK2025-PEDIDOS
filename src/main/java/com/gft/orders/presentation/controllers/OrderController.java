package com.gft.orders.presentation.controllers;

import com.gft.orders.business.model.DTO.ReturnLinesDTO;
import com.gft.orders.business.service.OrderServices;
import com.gft.orders.business.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Manage custom orders")
public class OrderController {

    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of orders. Can be filtered using the view parameter.")
    public List<Order> getAllOrders() {

        return orderServices.findAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get orders by ID", description = "Returns a single order by its unique identifier")
    public Order getOrderById(@PathVariable UUID id) throws NoSuchFieldException {

        Optional<Order> optional = orderServices.findOrderById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new NoSuchFieldException("Order ID not found");
    }

    @PostMapping
    @Operation(summary = "Create a order", description = "Creates a new order and returns the location of the new resource")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createOrder(@RequestBody Order order) {

        return orderServices.createOrder(order);

    }

    @PostMapping("/{orderId}/return")
    public BigDecimal returnOrder(@PathVariable UUID orderId) {

        return orderServices.createOrderReturn(orderId);
    }

    @PostMapping("/{orderId}/return-lines")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal returnOrderLines(@PathVariable UUID orderId, @RequestBody ReturnLinesDTO returnLinesDTO) {
        return orderServices.processReturnLines(orderId, returnLinesDTO);
    }
}