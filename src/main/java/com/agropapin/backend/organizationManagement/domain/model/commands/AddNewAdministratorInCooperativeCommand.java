package com.agropapin.backend.organizationManagement.domain.model.commands;

public record AddNewAdministratorInCooperativeCommand(
        Long cooperativeId,
        Long newAdministratorId,
        Long performedByUserId
) {
}
