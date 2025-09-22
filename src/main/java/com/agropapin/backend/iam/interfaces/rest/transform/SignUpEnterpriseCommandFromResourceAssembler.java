package com.agropapin.backend.iam.interfaces.rest.transform;

import com.fromzero.backend.iam.domain.model.commands.SignUpEnterpriseCommand;
import com.fromzero.backend.iam.interfaces.rest.resources.SignUpEnterpriseResource;


public class SignUpEnterpriseCommandFromResourceAssembler {
    public static SignUpEnterpriseCommand toCommandFromResource(SignUpEnterpriseResource resource) {
        return new SignUpEnterpriseCommand(resource.username(), resource.password(), resource.enterpriseName());
    }
}