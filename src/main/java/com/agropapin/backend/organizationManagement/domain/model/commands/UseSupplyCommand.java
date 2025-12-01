package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record UseSupplyCommand(
        UUID itemId,
        double amountToUse,
        String purpose
) {
}
