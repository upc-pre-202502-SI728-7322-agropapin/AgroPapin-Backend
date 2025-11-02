package com.agropapin.backend.organizationManagement.domain.model.commands;

public record UpdateAdministratorByUserIdCommand(
        String userId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber
) {
}
