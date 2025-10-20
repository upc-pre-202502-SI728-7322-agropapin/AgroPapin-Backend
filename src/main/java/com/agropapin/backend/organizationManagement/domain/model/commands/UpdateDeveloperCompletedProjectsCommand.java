package com.agropapin.backend.organizationManagement.domain.model.commands;


import com.agropapin.backend.organizationManagement.domain.model.aggregates.Developer;

public record UpdateDeveloperCompletedProjectsCommand(Developer developer, int addProject) {
}
