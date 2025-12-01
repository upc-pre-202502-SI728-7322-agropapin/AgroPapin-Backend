package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.enums.CropStatus;

import java.util.Date;
import java.util.UUID;

public record UpdatedPlantingResource(
        UUID id,
        Date plantingDate,
        Date actualHarvestDate,
        CropStatus status,
        UUID plotId
) {
}
