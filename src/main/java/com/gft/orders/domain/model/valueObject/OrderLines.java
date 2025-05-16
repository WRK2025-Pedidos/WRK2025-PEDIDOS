package com.gft.orders.domain.model.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Generated;

import java.math.BigDecimal;
import java.util.UUID;

@Generated
@Data
@Embeddable
public class OrderLines {

  private UUID product;
  private int quantity;
  private Double lineWeight;
  private BigDecimal productPrice;
  private BigDecimal linePrice;
  
}

