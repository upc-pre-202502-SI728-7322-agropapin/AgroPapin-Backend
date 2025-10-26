package com.agropapin.backend.organizationManagement.domain.model.commands;

public record DeleteCooperativeCommand(
        Long cooperativeId,
        Long deletedByUserId
) {
}
