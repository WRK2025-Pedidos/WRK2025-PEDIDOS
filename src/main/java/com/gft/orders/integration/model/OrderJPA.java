package com.gft.orders.integration.model;

import com.gft.orders.business.model.OrderOffer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Generated
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class OrderJPA {

    @Id
    private UUID id;

    private UUID cartId;
    private BigDecimal totalPrice;
    private Double countryTax;
    private Double paymentMethod;
    private LocalDateTime creationDate;
  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLineJPA> orderLines;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOfferJPA> orderOffers;

    private Boolean orderReturn;

    @ElementCollection
    @MapKeyColumn(name = "product_id")
    @Column(name = "returned_quantity")
    private Map<Long, Integer> returnedProductQuantity = new HashMap<>();

}
