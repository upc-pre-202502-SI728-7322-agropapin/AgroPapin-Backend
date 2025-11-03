package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.math.BigDecimal;

public record CreateFieldResource(
        String fieldName,
        String location,
        BigDecimal area

) {
}
