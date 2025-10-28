package com.agropapin.backend.organizationManagement.domain.model.queries;

import java.util.UUID;

public record GetAdministratorByUserIdAsyncQuery(
        UUID userId
) {
}
