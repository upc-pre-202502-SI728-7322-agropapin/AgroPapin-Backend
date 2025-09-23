package com.agropapin.backend.iam.domain.services;

import com.agropapin.backend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
