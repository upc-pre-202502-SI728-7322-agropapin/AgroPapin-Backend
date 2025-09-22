package com.agropapin.backend.iam.interfaces.rest.transform;


import com.fromzero.backend.iam.domain.model.aggregates.User;
import com.fromzero.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), user.getSerializedRoles(), token);
    }
}
