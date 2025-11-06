package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.FieldResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.PlotResource;

import java.util.List;
import java.util.stream.Collectors;

public class PlotResourceFromEntityAssembler {
    public static PlotResource toResourceFromEntity(Plot entity) {
        return new PlotResource(
                entity.getId(),
                entity.getPlotName(),
                entity.getArea(),
                entity.getStatus()
        );
    }

    public static List<PlotResource> toResourcesFromEntities(List<Plot> entities) {
        return entities.stream()
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
