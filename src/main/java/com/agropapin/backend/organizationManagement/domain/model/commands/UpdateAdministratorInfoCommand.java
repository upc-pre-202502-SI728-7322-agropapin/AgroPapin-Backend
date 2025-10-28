package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record UpdateAdministratorInfoCommand(
        UUID administratorId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
