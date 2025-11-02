package com.agropapin.backend.organizationManagement.application.internal.commandservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateFarmerCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerByUserIdCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerInfoCommand;
import com.agropapin.backend.organizationManagement.domain.services.FarmerCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.FarmerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmerCommandServiceImpl implements FarmerCommandService {

    private final FarmerRepository farmerRepository;

    public FarmerCommandServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    @Transactional
    public Optional<Farmer> handle(CreateFarmerCommand createFarmerCommand) {
        if (farmerRepository.existsFarmerByUserId(createFarmerCommand.userId())) {
            throw new IllegalArgumentException(
                    "A Farmer already exists for user id " + createFarmerCommand.userId());
        }

        var farmer = new Farmer(
                createFarmerCommand.email(),
                createFarmerCommand.userId()
        );

        var savedFarmer = farmerRepository.save(farmer);
        return Optional.of(savedFarmer);
    }

    @Override
    @Transactional
    public Optional<Farmer> handle(UpdateFarmerInfoCommand updateFarmerInfoCommand) {
        var farmer = farmerRepository.findById(updateFarmerInfoCommand.farmerId());

        if (farmer.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Farmer found with id " + updateFarmerInfoCommand.farmerId());
        }

        return farmer.map(farmerData -> {
            farmerData.updatePersonalInfo(
                    updateFarmerInfoCommand.firstName(),
                    updateFarmerInfoCommand.lastName(),
                    updateFarmerInfoCommand.country(),
                    updateFarmerInfoCommand.phoneNumber()
            );
            return farmerRepository.save(farmerData);
        });
    }

    @Override
    @Transactional
    public Optional<Farmer> handle(UpdateFarmerByUserIdCommand updateFarmerByUserIdCommand) {
        var farmer = farmerRepository.findFarmerByUserId(updateFarmerByUserIdCommand.userId());

        if (farmer.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Farmer found with id " + updateFarmerByUserIdCommand.userId());
        }

        return farmer.map(farmerData -> {
            farmerData.updatePersonalInfo(
                    updateFarmerByUserIdCommand.firstName(),
                    updateFarmerByUserIdCommand.lastName(),
                    updateFarmerByUserIdCommand.country(),
                    updateFarmerByUserIdCommand.phoneNumber()
            );
            return farmerRepository.save(farmerData);
        });
    }
}
