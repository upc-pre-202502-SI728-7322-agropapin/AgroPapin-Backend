package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerInfoCommand;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateFarmerResource;

public class UpdateFarmerCommandFromResourceAssembler {
    public static UpdateFarmerInfoCommand toCommandFromResource(Long farmerId, UpdateFarmerResource resource) {
        return new UpdateFarmerInfoCommand(
                farmerId,
                resource.firstName(),
                resource.lastName(),
                resource.country(),
                resource.phone()
        );
    }
}
