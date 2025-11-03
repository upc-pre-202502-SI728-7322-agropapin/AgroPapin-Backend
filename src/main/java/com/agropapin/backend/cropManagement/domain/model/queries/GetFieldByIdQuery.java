package com.agropapin.backend.cropManagement.domain.model.queries;

import java.util.UUID;

public record GetFieldByIdQuery(
        UUID fieldID
) {
}
