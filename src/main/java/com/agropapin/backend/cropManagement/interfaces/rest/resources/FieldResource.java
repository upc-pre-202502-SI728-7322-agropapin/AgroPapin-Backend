package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.enums.FieldStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record FieldResource(
        UUID fieldId,
        String fieldName,
        String location,
        BigDecimal totalArea,
        FieldStatus status
) {
}
