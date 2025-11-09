package com.agropapin.backend.devicemanagement.interfaces.rest.transform;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateSensorResource;

public class CreateSensorCommandFromResourceAssembler {
    public static CreateSensorCommand toCommandFromResource(CreateSensorResource resource) {
        return new CreateSensorCommand(
                resource.serialNumber(),
                resource.plotId(),
                resource.model(),
                resource.version()
        );
    }
}
