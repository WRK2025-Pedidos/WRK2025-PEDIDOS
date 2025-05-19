package com.gft.orders.application.controller;

import com.gft.orders.application.dto.OrderDTO;
import com.gft.orders.application.service.OrderServices;
import com.gft.orders.domain.model.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
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
            return ResponseEntity.ok(orderServices.findAllOrders());
        }

        return ResponseEntity.badRequest().body("El parametro no esta soportado: " + view);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        Optional<Order> optional = orderServices.findOrderById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO, UriComponentsBuilder ucb) {
        UUID id = orderServices.createOrder(orderDTO);

        URI uri = ucb.path("/orders/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();

    }

}