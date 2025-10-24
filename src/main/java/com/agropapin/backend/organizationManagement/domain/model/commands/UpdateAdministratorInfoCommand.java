package com.agropapin.backend.organizationManagement.domain.model.commands;

public record UpdateAdministratorInfoCommand(
        Long administratorId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
