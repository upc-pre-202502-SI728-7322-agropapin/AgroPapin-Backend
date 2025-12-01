package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.ProductType;

public record UpdateProductResource(
        String name,
        ProductType type,
        double amount,
        String unit
) {
}
