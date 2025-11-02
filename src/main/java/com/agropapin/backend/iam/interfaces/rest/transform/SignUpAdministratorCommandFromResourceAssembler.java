package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.Auth0RegistrationWebhookResource;
import com.agropapin.backend.iam.interfaces.rest.resources.SignUpAdministratorResource;


public class SignUpAdministratorCommandFromResourceAssembler {
    public static SignUpAdministratorCommand toCommandFromResource(Auth0RegistrationWebhookResource resource) {
        return new SignUpAdministratorCommand(resource.email(),resource.auth0UserId());
    }
}