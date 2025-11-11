package com.agropapin.backend.telemetryingestion.interfaces.rest.transform;

import com.agropapin.backend.telemetryingestion.domain.model.valueobjects.PlotReading;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.PlotReadingResource;

import java.util.List;
import java.util.stream.Collectors;

public class PlotReadingAssembler {

    public static PlotReading toValueObjectFromResource(PlotReadingResource resource) {
        return new PlotReading(
                resource.plotId(),
                resource.serialNumber(),
                resource.timestamp(),
                resource.temperature(),
                resource.humidity(),
                resource.soilMoisture()
        );
    }

    public static List<PlotReading> toValueObjectsFromResources(List<PlotReadingResource> resources) {
        return resources.stream()
                .map(PlotReadingAssembler::toValueObjectFromResource)
                .collect(Collectors.toList());
    }
}
