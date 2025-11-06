package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateFieldCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldStatusCommand;

import java.util.Optional;

public interface FieldCommandService {
    Optional<Field> handle(CreateFieldCommand createNewFieldCommand);
    Optional<Field> handle(UpdateFieldDataCommand updateFieldDataCommand);
    Optional<Field> handle(UpdateFieldStatusCommand updateFieldStatusCommand);
}
