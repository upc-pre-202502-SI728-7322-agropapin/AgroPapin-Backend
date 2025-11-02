package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record CreateCooperativeCommand(
        String name,
        String createdByUserId
) {
}
