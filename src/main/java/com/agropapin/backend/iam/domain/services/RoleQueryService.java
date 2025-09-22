package com.agropapin.backend.iam.domain.services;

import com.fromzero.backend.iam.domain.model.entities.Role;
import com.fromzero.backend.iam.domain.model.queries.GetAllRolesQuery;
import com.fromzero.backend.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
