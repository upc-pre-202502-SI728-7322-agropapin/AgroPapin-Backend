package com.agropapin.backend.iam.interfaces.rest.transform;

import com.fromzero.backend.iam.domain.model.entities.Role;
import com.fromzero.backend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}