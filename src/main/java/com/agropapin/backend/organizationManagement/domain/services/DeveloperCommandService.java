package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Developer;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateDeveloperCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateDeveloperCompletedProjectsCommand;

import java.util.Optional;

public interface DeveloperCommandService {
    Optional<Developer>handle(UpdateDeveloperCommand command);
    Optional<Developer> handle(UpdateDeveloperCompletedProjectsCommand command);
}
