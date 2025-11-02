package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record CreateFarmerCommand(
        String email,
        String userId
) {
}
