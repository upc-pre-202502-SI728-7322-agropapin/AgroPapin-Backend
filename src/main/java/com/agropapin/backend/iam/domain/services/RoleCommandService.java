package com.agropapin.backend.iam.domain.services;

import com.fromzero.backend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
