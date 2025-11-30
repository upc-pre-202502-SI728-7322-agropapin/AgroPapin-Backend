package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record RemoveMemberFromCooperativeCommand(
        UUID cooperativeId,
        String memberId,
        String removedByUserId
) {
}
