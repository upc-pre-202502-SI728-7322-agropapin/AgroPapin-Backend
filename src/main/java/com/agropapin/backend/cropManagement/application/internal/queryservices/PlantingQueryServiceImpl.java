package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.IrrigationPolicy;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlantingByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetIrrigationRulesByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlantingByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.PlantingQueryService;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.IrrigationRule;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.IrrigationPolicyRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlantingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantingQueryServiceImpl implements PlantingQueryService {
    private final PlantingRepository plantingRepository;
    private final IrrigationPolicyRepository irrigationPolicyRepository;

    public PlantingQueryServiceImpl(PlantingRepository plantingRepository, IrrigationPolicyRepository irrigationPolicyRepository) {
        this.plantingRepository = plantingRepository;
        this.irrigationPolicyRepository = irrigationPolicyRepository;
    }

    @Override
    public List<Planting> handle(GetAllPlantingByPlotIdQuery query) {
        return plantingRepository.findAllByPlotId(query.plotId());
    }

    @Override
    public Optional<Planting> handle(GetPlantingByIdQuery query) {
        return plantingRepository.findById(query.plantingId());
    }

    @Override
    public Optional<List<IrrigationRule>> handle(GetIrrigationRulesByPlotIdQuery query){
        var planting = this.plantingRepository.findTopByPlotIdAndStatusGrowingOrderByPlantingDateDesc(query.plotId());

        if (planting.isEmpty()) {
            return Optional.empty();
        }

        var cropTypeId = planting.get().getCropType().getId();

        var irrigationPolicy = this.irrigationPolicyRepository.findByCropTypeId(cropTypeId);

        return irrigationPolicy.map(IrrigationPolicy::getRules);

    }
}
