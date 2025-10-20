package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record EnterpriseResource(
        Long id,
        String enterpriseName,
        String description,
        String country,
        String ruc,
        String phone,
        String website,
        String profileImgUrl,
        String sector,
        Long userId
) {
}
