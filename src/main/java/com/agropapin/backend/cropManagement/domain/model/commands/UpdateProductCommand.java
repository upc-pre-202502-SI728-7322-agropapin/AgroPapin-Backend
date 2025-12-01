package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.ProductType;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;

import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        String name,
        ProductType type,
        Quantity quantity
) {
}
