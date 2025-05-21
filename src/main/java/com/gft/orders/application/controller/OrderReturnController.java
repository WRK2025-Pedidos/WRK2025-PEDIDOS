package com.gft.orders.application.controller;

import com.gft.orders.application.service.OrderReturnServices;
import com.gft.orders.domain.model.entity.OrderReturn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders_returns")
@Tag(name = "OrderReturn", description = "Manage operations related to orders returns")
public class OrderReturnController {
    private final OrderReturnServices orderReturnServices;

    public OrderReturnController(OrderReturnServices orderReturnServices) {
        this.orderReturnServices = orderReturnServices;
    }

    @PostMapping("/{id}")
    @Operation(summary = "Post orders return by ID", description = "Returns a single order return identified by its unique identifier")
    // TODO Al coger el pedido, recogemos de datos el id(Numero del producto que hay dentro del pedido), idOrder(Numero del pedido ) y quantity(cantidad de producto que queremos devolver) - @RequestBody  UUID idOrder, @RequestBody int quantity
    public ResponseEntity<?> createOrderReturn(@PathVariable UUID id ) {
        Optional<OrderReturn> optional = orderReturnServices.findOrderReturnById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

}
