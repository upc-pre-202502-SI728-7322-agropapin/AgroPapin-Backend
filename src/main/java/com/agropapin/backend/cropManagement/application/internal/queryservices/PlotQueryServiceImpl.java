package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlotByFieldIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlotByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.PlotQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class PlotQueryServiceImpl implements PlotQueryService {

    private final PlotRepository plotRepository;

    public PlotQueryServiceImpl(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    @Override
    public Optional<Plot> handle(GetPlotByIdQuery getPlotByIdQuery) {
        return plotRepository.findPlotByIdAndFieldId(getPlotByIdQuery.plotId() ,getPlotByIdQuery.fieldId());
    }

    @Override
    public Optional<List<Plot>> handle(GetAllPlotByFieldIdQuery getAllPlotByFieldIdQuery) {
        return plotRepository.findAllByFieldId(getAllPlotByFieldIdQuery.fieldId());
    }
}
