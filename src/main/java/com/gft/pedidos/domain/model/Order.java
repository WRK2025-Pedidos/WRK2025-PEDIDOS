package com.gft.pedidos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@NoArgsConstructor
public class Order {
    UUID id;
    UUID idCart;
    LocalDate creationDate;
    List<OrderLines> orderLines;
    List<UUID> offers;

}
