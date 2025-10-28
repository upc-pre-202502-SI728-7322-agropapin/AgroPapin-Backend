package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorInfoCommand;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateAdministratorResource;

public class UpdateAdministratorCommandFromResourceAssembler {
    public static UpdateAdministratorInfoCommand toCommandFromResource(Long administratorId, UpdateAdministratorResource resource) {
        return new UpdateAdministratorInfoCommand(
                administratorId,
                resource.firstName(),
                resource.lastName(),
                resource.country(),
                resource.phone()
        );
    }
}
