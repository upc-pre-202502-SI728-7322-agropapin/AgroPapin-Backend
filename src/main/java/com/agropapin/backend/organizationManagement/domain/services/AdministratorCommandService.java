package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateAdministratorCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorByUserIdCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorInfoCommand;

import java.util.Optional;

public interface AdministratorCommandService {
    Optional<Administrator> handle(CreateAdministratorCommand createAdministratorCommand);
    Optional<Administrator> handle(UpdateAdministratorInfoCommand updateAdministratorInfoCommand);
    Optional<Administrator> handle(UpdateAdministratorByUserIdCommand updateAdministratorByUserIdCommand);
}
