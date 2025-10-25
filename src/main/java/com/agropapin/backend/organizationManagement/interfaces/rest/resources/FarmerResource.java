package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record FarmerResource(
        Long farmerId,
        String firstName,
        String lastName,
        String country,
        String phone,
        Long userId,
        Long cooperativeId
) {
}
