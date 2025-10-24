package com.agropapin.backend.organizationManagement.domain.services;

import java.util.Optional;

public interface DeveloperCommandService {
    Optional<Developer>handle(UpdateDeveloperCommand command);
    Optional<Developer> handle(UpdateDeveloperCompletedProjectsCommand command);
}