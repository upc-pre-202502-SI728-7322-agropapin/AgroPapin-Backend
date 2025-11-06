package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.FieldStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateFieldStatusResource(
        @NotNull(message = "Status is required")
        FieldStatus status
) {
}
