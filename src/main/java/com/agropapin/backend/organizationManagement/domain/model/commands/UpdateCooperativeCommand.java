package com.agropapin.backend.organizationManagement.domain.model.commands;

public record UpdateCooperativeCommand(
        Long cooperativeId,
        String name
) {
}
