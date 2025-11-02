package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record UpdateFarmerByUserIdCommand(
        String userId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
