package com.gft.orders.application.controller;

import com.gft.orders.application.dto.OrderResponse;
import com.gft.orders.application.service.OrderServices;
import com.gft.orders.domain.model.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Manage custom orders")
public class OrderController {

    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of orders. Can be filtered using the view parameter.")
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false, defaultValue = "ALL") String view) {

        view = view.toUpperCase();
        if ("ALL".equals(view)) {
            return ResponseEntity.ok(orderServices.findAllOrders());
        }

        return ResponseEntity.badRequest().body("Unsupported view parameter: " + view);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get orders by ID", description = "Returns a single order by its unique identifier")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        Optional<Order> optional = orderServices.findOrderById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a order", description = "Creates a new order and returns the location of the new resource")
    public ResponseEntity<?> createOrder(@RequestBody OrderResponse orderResponse, UriComponentsBuilder ucb) {
        UUID id = orderServices.createOrder(orderResponse);

        URI uri = ucb.path("/orders/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

}