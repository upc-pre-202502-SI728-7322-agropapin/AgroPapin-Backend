package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.DeleteCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateCooperativeCommand;

import java.util.Optional;

public interface CooperativeCommandService {
    Optional<Cooperative> handle(CreateCooperativeCommand createCooperativeCommand);
    Optional<Cooperative> handle(UpdateCooperativeCommand command);
    Optional<Cooperative> handle(DeleteCooperativeCommand deleteCooperativeCommand);
}
