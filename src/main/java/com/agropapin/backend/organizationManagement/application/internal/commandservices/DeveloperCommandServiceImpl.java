package com.agropapin.backend.organizationManagement.application.internal.commandservices;


import com.agropapin.backend.organizationManagement.domain.model.aggregates.Developer;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateDeveloperCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateDeveloperCompletedProjectsCommand;
import com.agropapin.backend.organizationManagement.domain.services.DeveloperCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeveloperCommandServiceImpl implements DeveloperCommandService {

    private final DeveloperRepository developerRepository;

    public DeveloperCommandServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }


    @Override
    public Optional<Developer> handle(UpdateDeveloperCompletedProjectsCommand command) {
        var updatedDeveloper = command.developer();
        updatedDeveloper.setCompletedProjects(command.addProject());
        this.developerRepository.save(updatedDeveloper);
        return Optional.of(updatedDeveloper);
    }

    @Override
    public Optional<Developer> handle(UpdateDeveloperCommand command) {
        var developer = developerRepository.findDeveloperByUser_Id(command.developerId())
                .orElseThrow(() -> new IllegalArgumentException("Developer with id " + command.developerId() + " not found"));
        developer.setFirstName(command.firstName());
        developer.setLastName(command.lastName());
        developer.setDescription(command.description());
        developer.setCountry(command.country());
        developer.setPhone(command.phone());
        developer.setSpecialties(command.specialties());
        developer.setProfileImgUrl(command.profileImgUrl());

        developerRepository.save(developer);

        return Optional.of(developer);
    }
}
