package com.gft.orders.business.model.DTO;

import java.util.Map;
import java.util.UUID;

public record ReturnLineDTO(UUID original_order,
                            Map<UUID, Integer> returnLine) {
}
