package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record UpdateFarmerInfoCommand(
        UUID farmerId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
