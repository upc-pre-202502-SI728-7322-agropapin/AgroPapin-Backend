package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorInfoCommand;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateAdministratorResource;

import java.util.UUID;

public class UpdateAdministratorCommandFromResourceAssembler {
    public static UpdateAdministratorInfoCommand toCommandFromResource(UUID administratorId, UpdateAdministratorResource resource) {
        return new UpdateAdministratorInfoCommand(
                administratorId,
                resource.firstName(),
                resource.lastName(),
                resource.country(),
                resource.phone()
        );
    }
}
