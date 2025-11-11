package com.agropapin.backend.devicemanagement.interfaces.rest.transform;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateActuatorResource;

public class CreateActuatorCommandFromResourceAssembler {
    public static CreateActuatorCommand toCommandFromResource(CreateActuatorResource resource) {
        return new CreateActuatorCommand(
                resource.serialNumber(),
                resource.plotId(),
                resource.model(),
                resource.version(),
                resource.actuatorType()
        );
    }
}
