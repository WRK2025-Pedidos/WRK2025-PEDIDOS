package com.gft.orders.domain.model.valueObject;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Data
@Embeddable
public class OrderLine {

  private UUID product;
  private int quantity;
  private Double lineWeight;
  private BigDecimal productPrice;
  private BigDecimal linePrice;
  
}

