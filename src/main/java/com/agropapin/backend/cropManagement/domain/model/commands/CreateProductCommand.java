package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.ProductType;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public record CreateProductCommand(
        String name,
        LocalDate applicationDate,
        ProductType type,
        Quantity quantity,
        UUID plantingId,
        UUID plotId
) {
}
