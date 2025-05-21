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
@Tag(name = "OrdersReturn", description = "Manage operations related to orders returns")
public class OrderReturnController {

}
