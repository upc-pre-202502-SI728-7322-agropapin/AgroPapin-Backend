package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import java.util.UUID;

public record AdministratorResource(
        UUID administratorId,
        String firstName,
        String lastName,
        String country,
        String phone,
        String userId,
        UUID cooperativeId
) {
}
