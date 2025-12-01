package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateControlCommand;

import java.util.Optional;

public interface ControlCommandService {
    Optional<Control> handle(CreateControlCommand command);
    Optional<Control> handle(UpdateControlCommand command);
    boolean handle(DeleteControlCommand command);
}
