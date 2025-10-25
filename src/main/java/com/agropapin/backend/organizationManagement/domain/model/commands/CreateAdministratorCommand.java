package com.agropapin.backend.organizationManagement.domain.model.commands;

public record CreateAdministratorCommand(
        String firstName,
        String lastName,
        String country,
        String phone,
        Long userId
) {
}
