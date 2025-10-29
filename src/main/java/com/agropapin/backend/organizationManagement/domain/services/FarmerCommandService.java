package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateFarmerCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerInfoCommand;

import java.util.Optional;

public interface FarmerCommandService {
    Optional<Farmer> handle(CreateFarmerCommand createFarmerCommand);
    Optional<Farmer> handle(UpdateFarmerInfoCommand command);
}
