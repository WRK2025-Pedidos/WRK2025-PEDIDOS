package com.gft.orders.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderLines {

  UUID product;
  int quantity;
  Boolean refund;
  Double LineWeight;
  Double LinePrice;
}
