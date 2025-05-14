package com.gft.orders.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderLines {

  UUID product;
  int quantity;
  Boolean refund;

}
