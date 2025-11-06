package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.math.BigDecimal;

public record UpdateFieldResource(
        String fieldName,
        String location,
        BigDecimal area
) {
}
