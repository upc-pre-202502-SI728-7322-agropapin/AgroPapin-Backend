package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record UpdateDeveloperCompletedProjectsResource(
        Long developerId,
        int addProject
) {
    public UpdateDeveloperCompletedProjectsResource {
        if (developerId == null) {
            System.out.println("developerId is null");
            throw new NullPointerException("developerId is null");
        }
        if (addProject == 0) {
            System.out.println("addProject is null");
            throw new NullPointerException("addProject is null");
        }
    }
}