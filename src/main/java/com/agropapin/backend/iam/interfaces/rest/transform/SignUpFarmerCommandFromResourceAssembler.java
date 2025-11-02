package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.Auth0RegistrationWebhookResource;

public class SignUpFarmerCommandFromResourceAssembler {
    public static SignUpFarmerCommand toCommandFromResource(Auth0RegistrationWebhookResource resource) {
        return new SignUpFarmerCommand(resource.email(), resource.auth0UserId());
    }
}