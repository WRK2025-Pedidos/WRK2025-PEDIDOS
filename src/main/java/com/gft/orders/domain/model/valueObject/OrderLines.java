package com.gft.orders.domain.model.valueObject;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderLines {

  UUID product;
  int quantity;
  Boolean refund;
  Double LineWeight;
  BigDecimal LinePrice;
}
