package com.agropapin.backend.organizationManagement.domain.model.commands;

public record AddNewMemberInCooperativeCommand(
        Long cooperativeId,
        Long newMemberId,
        Long performedByUserId
) {
}
