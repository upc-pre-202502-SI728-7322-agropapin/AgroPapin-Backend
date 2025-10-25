package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record AdministratorResource(
        Long administratorId,
        String firstName,
        String lastName,
        String country,
        String phone,
        Long userId,
        Long cooperativeId
) {
}
