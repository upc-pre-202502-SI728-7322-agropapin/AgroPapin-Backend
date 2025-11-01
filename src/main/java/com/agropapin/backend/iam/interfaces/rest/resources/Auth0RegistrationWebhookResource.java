package com.agropapin.backend.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0RegistrationWebhookResource(
        @JsonProperty("auth0_user_id")
        String auth0UserId,
        @JsonProperty("email")
        String email,
        @JsonProperty("role_type")
        String roleType
) {
}
