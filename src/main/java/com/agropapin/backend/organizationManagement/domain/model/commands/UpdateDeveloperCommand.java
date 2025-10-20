package com.agropapin.backend.organizationManagement.domain.model.commands;

public record UpdateDeveloperCommand(
        Long developerId,
        String firstName,
        String lastName,
        String description,
        String country,
        String phone,
        String specialties,
        String profileImgUrl
) {
}



