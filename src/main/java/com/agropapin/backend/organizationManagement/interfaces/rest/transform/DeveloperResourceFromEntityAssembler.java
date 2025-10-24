package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.interfaces.rest.resources.DeveloperResource;

public class DeveloperResourceFromEntityAssembler {
    public static DeveloperResource toResourceFromEntity(Developer entity) {
        return new DeveloperResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDescription(),
                entity.getCountry(),
                entity.getPhone(),
                entity.getSpecialties(),
                entity.getProfileImgUrl(),
                entity.getUser().getId());
    }
}
