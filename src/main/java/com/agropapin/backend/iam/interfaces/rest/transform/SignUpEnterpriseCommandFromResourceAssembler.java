package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpEnterpriseCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.SignUpEnterpriseResource;


public class SignUpEnterpriseCommandFromResourceAssembler {
    public static SignUpEnterpriseCommand toCommandFromResource(SignUpEnterpriseResource resource) {
        return new SignUpEnterpriseCommand(resource.username(), resource.password(), resource.enterpriseName());
    }
}