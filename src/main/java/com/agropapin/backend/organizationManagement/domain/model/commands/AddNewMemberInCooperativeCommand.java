package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record AddNewMemberInCooperativeCommand(
        UUID cooperativeId,
        UUID newMemberId,
        UUID performedByUserId
) {
}
