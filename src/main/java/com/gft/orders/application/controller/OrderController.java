package com.gft.orders.application.controller;

import com.gft.orders.domain.model.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false, defaultValue = "ALL") String view) {
        Object response = null;

        view = view.toUpperCase();

        switch (view) {
            case "ALL": response = orderServices.getAll(); break;
            case "DTO": response = orderServices.getOrderDTO(); break;
            default: response = null;
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {

        Optional<Order> optional = orderServices.read(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
