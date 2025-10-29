package com.agropapin.backend.iam.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record AuthenticatedUserResource(
        UUID id,
        String email,
        List<String> roles,
        String token) {
}
