package com.agropapin.backend.devicemanagement.interfaces.rest.transform;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.ActuatorResource;

import java.util.List;
import java.util.stream.Collectors;

public class ActuatorResourceFromEntityAssembler {
    public static ActuatorResource toResourceFromEntity(Actuator entity){
        return new ActuatorResource(
                entity.getId(),
                entity.getSerialNumber(),
                entity.getPlotId(),
                entity.getStatus().name(),
                entity.getDeviceModel().getModel(),
                entity.getDeviceModel().getVersion(),
                entity.getActuatorType().name()
        );
    }

    public static List<ActuatorResource> toResourceFromEntities(List<Actuator> entities){
        return entities.stream()
                .map(ActuatorResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
