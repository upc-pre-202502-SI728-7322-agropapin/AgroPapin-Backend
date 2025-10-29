package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.commands.*;

import java.util.Optional;

public interface CooperativeCommandService {
    Optional<Cooperative> handle(CreateCooperativeCommand createCooperativeCommand);
    Optional<Cooperative> handle(UpdateCooperativeCommand updateCooperativeCommand);
    Optional<Cooperative> handle(DeleteCooperativeCommand deleteCooperativeCommand);
    Optional<Cooperative> handle(AddNewAdministratorInCooperativeCommand addNewAdministratorInCooperativeCommand);
    Optional<Cooperative> handle(AddNewMemberInCooperativeCommand addNewMemberInCooperativeCommand);
}
