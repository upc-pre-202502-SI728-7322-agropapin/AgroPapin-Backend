package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.enums.CropStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePlantingStatusResource(
        @NotNull(message = "Plot status is required")
        CropStatus status
) {
}
