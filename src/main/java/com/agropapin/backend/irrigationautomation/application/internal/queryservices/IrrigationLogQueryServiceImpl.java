package com.agropapin.backend.irrigationautomation.application.internal.queryservices;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.domain.model.queries.GetIrrigationLogsByPlotIdQuery;
import com.agropapin.backend.irrigationautomation.domain.model.repositories.IrrigationLogRepository;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationLogQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IrrigationLogQueryServiceImpl implements IrrigationLogQueryService {

    private final IrrigationLogRepository irrigationLogRepository;

    public IrrigationLogQueryServiceImpl(IrrigationLogRepository irrigationLogRepository) {
        this.irrigationLogRepository = irrigationLogRepository;
    }

    @Override
    public List<IrrigationLog> handle(GetIrrigationLogsByPlotIdQuery query) {
        return irrigationLogRepository.findByPlotIdOrderByDecisionTimestampDesc(query.plotId());
    }
}
