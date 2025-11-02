package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record AddNewAdministratorInCooperativeCommand(
        UUID cooperativeId,
        String newAdministratorId,
        String performedByUserId
) {
}
