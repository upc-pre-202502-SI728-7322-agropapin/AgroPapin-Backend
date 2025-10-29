package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record UpdateFarmerResource(
       String firstName,
       String lastName,
       String country,
       String phone
) {
}
