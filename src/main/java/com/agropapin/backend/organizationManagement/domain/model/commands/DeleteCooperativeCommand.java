package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record DeleteCooperativeCommand(
        UUID cooperativeId,
        UUID deletedByUserId
) {
}
