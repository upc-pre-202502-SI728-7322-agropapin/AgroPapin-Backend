package com.agropapin.backend.irrigationautomation.domain.model.services;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.domain.model.commands.CreateIrrigationLogCommand;

import java.util.Optional;

public interface IrrigationLogCommandService {
    Optional<IrrigationLog> handle(CreateIrrigationLogCommand command);
}
