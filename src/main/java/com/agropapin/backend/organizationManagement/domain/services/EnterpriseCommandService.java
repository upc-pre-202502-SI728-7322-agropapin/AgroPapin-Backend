package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Enterprise;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateEnterpriseCommand;

import java.util.Optional;

public interface EnterpriseCommandService {
    Optional<Enterprise> handle(UpdateEnterpriseCommand command);

}
