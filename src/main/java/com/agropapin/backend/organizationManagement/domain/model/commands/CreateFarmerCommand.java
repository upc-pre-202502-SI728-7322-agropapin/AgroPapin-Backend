package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record CreateFarmerCommand(
        String firstName,
        String lastName,
        String country,
        String phone,
        UUID userId
) {
}
