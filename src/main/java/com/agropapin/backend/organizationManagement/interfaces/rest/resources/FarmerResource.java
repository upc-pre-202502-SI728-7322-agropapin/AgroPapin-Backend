package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import java.util.UUID;

public record FarmerResource(
        UUID farmerId,
        String firstName,
        String lastName,
        String country,
        String phone,
        UUID userId,
        UUID cooperativeId
) {
}
