package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record CreateAdministratorCommand(
        String email,
        String userId
) {
}
