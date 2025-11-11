package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.CropStatus;

import java.util.Date;
import java.util.UUID;

public record PlantingResource(
        UUID id,
        Date plantingDate,
        Date actualHarvestDate,
        CropStatus status,
        UUID plotId
) {
}
