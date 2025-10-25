package com.agropapin.backend.organizationManagement.domain.model.commands;

public record CreateCooperativeCommand(
        String name,
        Long createdByUserId
) {
}
