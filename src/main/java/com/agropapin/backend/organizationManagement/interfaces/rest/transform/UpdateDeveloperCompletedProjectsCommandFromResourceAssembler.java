package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateDeveloperCompletedProjectsResource;

public class UpdateDeveloperCompletedProjectsCommandFromResourceAssembler {
    private final DeveloperRepository developerRepository;

    public UpdateDeveloperCompletedProjectsCommandFromResourceAssembler(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public UpdateDeveloperCompletedProjectsCommand toCommandFromResource(UpdateDeveloperCompletedProjectsResource resource) {
        Developer developer = developerRepository.findById(resource.developerId())
                .orElseThrow(() -> new RuntimeException("Developer not found")); // Reemplaza esto con tu propia excepción personalizada

        return new UpdateDeveloperCompletedProjectsCommand(
                developer,
                resource.addProject()
        );
    }
}