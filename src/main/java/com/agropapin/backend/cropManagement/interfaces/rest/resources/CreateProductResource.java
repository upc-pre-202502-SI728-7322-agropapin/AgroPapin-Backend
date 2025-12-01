package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.ProductType;

import java.time.LocalDate;
import java.util.UUID;

public record CreateProductResource(
        String name,
        LocalDate applicationDate,
        ProductType type,
        double amount,
        String unit,
        UUID plantingId
) {
}
