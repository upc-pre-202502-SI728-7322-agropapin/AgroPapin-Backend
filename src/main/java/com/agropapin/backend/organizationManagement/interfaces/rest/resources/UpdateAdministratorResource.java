package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record UpdateAdministratorResource(
        String firstName,
        String lastName,
        String country,
        String phone
) {
}
