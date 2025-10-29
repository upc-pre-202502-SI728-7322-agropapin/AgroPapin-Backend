package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.SignUpAdministratorResource;


public class SignUpAdministratorCommandFromResourceAssembler {
    public static SignUpAdministratorCommand toCommandFromResource(SignUpAdministratorResource resource) {
        return new SignUpAdministratorCommand(resource.email(), resource.password(), resource.firstName(), resource.lastName(), resource.country(), resource.phone());
    }
}