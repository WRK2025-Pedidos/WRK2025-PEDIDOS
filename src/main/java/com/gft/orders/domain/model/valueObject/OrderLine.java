package com.gft.orders.domain.model.valueObject;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Embeddable
public class OrderLine {

  private UUID product;
  private int quantity;
  private Double LineWeight;
  private BigDecimal LinePrice;
  
}

