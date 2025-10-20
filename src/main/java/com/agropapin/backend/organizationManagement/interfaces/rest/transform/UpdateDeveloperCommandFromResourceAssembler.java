package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateDeveloperCommand;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateDeveloperResource;

public class UpdateDeveloperCommandFromResourceAssembler {
    public static UpdateDeveloperCommand toCommandFromResource(Long developerId, UpdateDeveloperResource resource) {
        return new UpdateDeveloperCommand(
                developerId,
                resource.firstName(),
                resource.lastName(),
                resource.description(),
                resource.country(),
                resource.phone(),
                resource.specialties(),
                resource.profileImgUrl()
        );
    }
}
