package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateFieldCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldDataCommand;
import com.agropapin.backend.cropManagement.domain.model.services.FieldCommandService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.FieldRepository;
import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
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
    public Optional<Field> handle(CreateFieldCommand createNewFieldCommand) {
        return Optional.empty();
    }

    @Override
    public Optional<Field> handle(UpdateFieldDataCommand updateFieldDataCommand) {
        return Optional.empty();
    }
}
