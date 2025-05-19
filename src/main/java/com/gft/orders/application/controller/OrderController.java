package com.gft.orders.application.controller;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.application.service.OrderServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false, defaultValue = "ALL") String view) {
        Object response = null;

        view = view.toUpperCase();
        if (view.equals("ALL")) {
            response = orderServices.findAllOrders();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        return orderServices.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO, UriComponentsBuilder ucb) {
        UUID id = orderServices.createOrder(orderDTO);

        URI uri = ucb.path("/orders/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();

    }

}