package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.ControlQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.ControlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlQueryServiceImpl implements ControlQueryService {

    private final ControlRepository controlRepository;

    public ControlQueryServiceImpl(ControlRepository controlRepository) {
        this.controlRepository = controlRepository;
    }

    @Override
    public List<Control> handle(GetControlsByPlotIdQuery query) {
        return controlRepository.findByPlotId(query.plotId());
    }

    @Override
    public List<Control> handle(GetControlsByPlantingIdQuery query) {
        return controlRepository.findByPlantingId(query.plantingId());
    }
}
