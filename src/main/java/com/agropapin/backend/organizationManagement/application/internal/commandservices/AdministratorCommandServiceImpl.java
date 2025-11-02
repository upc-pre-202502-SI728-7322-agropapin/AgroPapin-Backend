package com.agropapin.backend.organizationManagement.application.internal.commandservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateAdministratorCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorInfoCommand;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.AdministratorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministratorCommandServiceImpl implements AdministratorCommandService {

    public final AdministratorRepository administratorRepository;

    public AdministratorCommandServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    @Transactional
    public Optional<Administrator> handle(CreateAdministratorCommand createAdministratorCommand) {
        if (administratorRepository.existsAdministratorByUserId(createAdministratorCommand.userId())){
            throw new IllegalArgumentException(
                    "A Administrator already exists for user id " + createAdministratorCommand.userId());
        }

        var administrator = new Administrator(
                createAdministratorCommand.email(),
                createAdministratorCommand.userId()
        );

        var savedAdministrator = administratorRepository.save(administrator);
        return Optional.of(savedAdministrator);
    }

    @Override
    public Optional<Administrator> handle(UpdateAdministratorInfoCommand updateAdministratorInfoCommand) {
        var administrator = administratorRepository.findById(updateAdministratorInfoCommand.administratorId());

        if (administrator.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Administrator found with id " + updateAdministratorInfoCommand.administratorId());
        }

        return administrator.map(administratorData -> {
            administratorData.updatePersonalInfo(
                    updateAdministratorInfoCommand.firstName(),
                    updateAdministratorInfoCommand.lastName(),
                    updateAdministratorInfoCommand.country(),
                    updateAdministratorInfoCommand.phoneNumber()
            );
            return administratorRepository.save(administratorData);
        });
    }
}
