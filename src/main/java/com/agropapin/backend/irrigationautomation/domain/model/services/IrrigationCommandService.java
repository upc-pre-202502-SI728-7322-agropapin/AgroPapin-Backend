package com.agropapin.backend.irrigationautomation.domain.model.services;

import com.agropapin.backend.irrigationautomation.domain.model.commands.ActivateIrrigationCommand;

public interface IrrigationCommandService {
    void handle(ActivateIrrigationCommand command);
}
