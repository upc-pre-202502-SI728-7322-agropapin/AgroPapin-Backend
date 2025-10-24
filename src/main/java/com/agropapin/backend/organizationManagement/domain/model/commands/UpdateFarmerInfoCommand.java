package com.agropapin.backend.organizationManagement.domain.model.commands;

public record UpdateFarmerInfoCommand(
        Long farmerId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
