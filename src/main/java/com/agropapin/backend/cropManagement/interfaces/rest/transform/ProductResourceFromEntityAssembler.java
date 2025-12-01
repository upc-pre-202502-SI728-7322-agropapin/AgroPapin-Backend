package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Product;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.ProductResource;

public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                entity.getName(),
                entity.getApplicationDate(),
                entity.getType(),
                entity.getQuantity().getAmount(),
                entity.getQuantity().getUnit(),
                entity.getPlantingId(),
                entity.getPlot().getId()
        );
    }
}
