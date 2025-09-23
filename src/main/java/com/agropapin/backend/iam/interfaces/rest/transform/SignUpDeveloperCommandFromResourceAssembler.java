package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpDeveloperCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.SignUpDeveloperResource;

public class SignUpDeveloperCommandFromResourceAssembler {
    public static SignUpDeveloperCommand toCommandFromResource(SignUpDeveloperResource resource) {
        return new SignUpDeveloperCommand(resource.username(), resource.password(), resource.firstName(), resource.lastName());
    }
}