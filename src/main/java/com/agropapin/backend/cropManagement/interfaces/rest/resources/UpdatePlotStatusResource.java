package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePlotStatusResource(
        @NotNull(message = "Plot status is required")
        PlotStatus status
) {
}
