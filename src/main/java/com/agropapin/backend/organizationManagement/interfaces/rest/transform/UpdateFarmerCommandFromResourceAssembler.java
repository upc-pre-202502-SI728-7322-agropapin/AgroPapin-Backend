package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerInfoCommand;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateFarmerResource;

import java.util.UUID;

public class UpdateFarmerCommandFromResourceAssembler {
    public static UpdateFarmerInfoCommand toCommandFromResource(UUID farmerId, UpdateFarmerResource resource) {
        return new UpdateFarmerInfoCommand(
                farmerId,
                resource.firstName(),
                resource.lastName(),
                resource.country(),
                resource.phone()
        );
    }
}
