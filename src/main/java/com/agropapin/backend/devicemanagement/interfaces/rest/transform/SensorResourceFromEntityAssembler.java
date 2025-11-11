package com.agropapin.backend.devicemanagement.interfaces.rest.transform;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.SensorResource;

import java.util.List;
import java.util.stream.Collectors;

public class SensorResourceFromEntityAssembler {
    public static SensorResource toResourceFromEntity(Sensor entity) {
        return new SensorResource(
                entity.getId(),
                entity.getSerialNumber(),
                entity.getPlotId(),
                entity.getStatus().name(),
                entity.getDeviceModel().getModel(),
                entity.getDeviceModel().getVersion()
        );
    }

    public static List<SensorResource> toResourceFromEntities(List<Sensor> entities) {
        return entities.stream()
                .map(SensorResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
