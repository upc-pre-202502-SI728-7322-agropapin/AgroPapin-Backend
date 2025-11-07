package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlantingByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlantingByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.PlantingQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlantingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantingQueryServiceImpl implements PlantingQueryService {
    private final PlantingRepository plantingRepository;

    public PlantingQueryServiceImpl(PlantingRepository plantingRepository) {
        this.plantingRepository = plantingRepository;
    }

    @Override
    public List<Planting> handle(GetAllPlantingByPlotIdQuery query) {
        return plantingRepository.findAllByPlotId(query.plotId());
    }

    @Override
    public Optional<Planting> handle(GetPlantingByIdQuery query) {
        return plantingRepository.findById(query.plantingId());
    }
}
