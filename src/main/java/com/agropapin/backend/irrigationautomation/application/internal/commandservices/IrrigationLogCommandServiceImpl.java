package com.agropapin.backend.irrigationautomation.application.internal.commandservices;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.domain.model.commands.CreateIrrigationLogCommand;
import com.agropapin.backend.irrigationautomation.domain.model.repositories.IrrigationLogRepository;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationLogCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IrrigationLogCommandServiceImpl implements IrrigationLogCommandService {

    private final IrrigationLogRepository irrigationLogRepository;

    public IrrigationLogCommandServiceImpl(IrrigationLogRepository irrigationLogRepository) {
        this.irrigationLogRepository = irrigationLogRepository;
    }

    @Override
    public Optional<IrrigationLog> handle(CreateIrrigationLogCommand command) {
        var logEntry = new IrrigationLog(
                command.plotId(),
                command.decision(),
                command.reason(),
                command.humidityReading(),
                command.humidityThreshold()
        );
        irrigationLogRepository.save(logEntry);
        return Optional.of(logEntry);
    }
}
