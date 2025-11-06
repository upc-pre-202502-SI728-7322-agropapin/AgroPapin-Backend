package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateFieldResource(
        @NotBlank(message = "Field name is required")
        String fieldName,

        @NotBlank(message = "Location is required")
        String location,

        @NotNull(message = "Area is required")
        @Positive(message = "Area must be positive")
        BigDecimal area
) {
}
