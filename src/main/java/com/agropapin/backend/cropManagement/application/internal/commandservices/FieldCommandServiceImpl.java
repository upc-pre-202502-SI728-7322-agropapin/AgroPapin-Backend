package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.exceptions.FieldNotFoundException;
import com.agropapin.backend.cropManagement.domain.exceptions.UnauthorizedFieldAccessException;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateFieldCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.services.FieldCommandService;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.FieldStatus;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.FieldRepository;
import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FieldCommandServiceImpl implements FieldCommandService {

    public final FieldRepository fieldRepository;
    public final IamContextFacade iamContextFacade;

    public FieldCommandServiceImpl(FieldRepository fieldRepository, IamContextFacade iamContextFacade) {
        this.fieldRepository = fieldRepository;
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    @Transactional
    public Optional<Field> handle(CreateFieldCommand createNewFieldCommand) {
        var fieldExisted = fieldRepository.findFieldByFarmerUserId(createNewFieldCommand.creatorUserId());

        if (fieldExisted.isPresent()) {
            throw new IllegalStateException("Farmer currently have a field");
        }

        var field = new Field(
                createNewFieldCommand.fieldName(),
                createNewFieldCommand.location(),
                createNewFieldCommand.area(),
                createNewFieldCommand.creatorUserId()
        );

        var savedField = this.fieldRepository.save(field);
        return Optional.of(savedField);
    }

    @Override
    public Optional<Field> handle(UpdateFieldDataCommand updateFieldDataCommand) {
        var field = fieldRepository.findFieldByFarmerUserId(updateFieldDataCommand.userId());

        if (field.isEmpty()) {
            throw new IllegalArgumentException(
                    "No field found with any user with userId: " + updateFieldDataCommand.userId());
        }

        return field.map(fieldData -> {
            fieldData.updateInfo(
                    updateFieldDataCommand.fieldName(),
                    updateFieldDataCommand.location(),
                    updateFieldDataCommand.area()
            );

            return fieldRepository.save(fieldData);
        });
    }

    @Override
    @Transactional
    public Optional<Field> handle(UpdateFieldStatusCommand updateFieldStatusCommand) {
        var field = fieldRepository.findById(updateFieldStatusCommand.fieldId())
                .orElseThrow(() -> new FieldNotFoundException(updateFieldStatusCommand.fieldId()));

        if (!field.getFarmerUserId().equals(updateFieldStatusCommand.farmerUserId())) {
            throw new UnauthorizedFieldAccessException("Not authorized to update this field");
        }

        if (!field.canBeModified() && updateFieldStatusCommand.newStatus() != FieldStatus.ACTIVE) {
            throw new IllegalStateException("Field under maintenance can only be activated");
        }

        field.updateStatus(updateFieldStatusCommand.newStatus());

        var savedField = fieldRepository.save(field);

        return Optional.of(savedField);
    }
}
