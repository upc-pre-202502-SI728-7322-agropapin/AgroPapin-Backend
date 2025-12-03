package com.agropapin.backend.devicemanagement.domain.model.queries;

import java.util.UUID;

public record GetActuatorByIdQuery(
        UUID actuatorId
) {
}
