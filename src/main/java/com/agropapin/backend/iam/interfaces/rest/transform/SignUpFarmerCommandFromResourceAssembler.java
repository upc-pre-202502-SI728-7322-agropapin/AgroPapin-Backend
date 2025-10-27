package com.agropapin.backend.iam.interfaces.rest.transform;

import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import com.agropapin.backend.iam.interfaces.rest.resources.SignUpFarmerResource;

public class SignUpFarmerCommandFromResourceAssembler {
    public static SignUpFarmerCommand toCommandFromResource(SignUpFarmerResource resource) {
        return new SignUpFarmerCommand(resource.email(), resource.password(), resource.firstName(), resource.lastName(), resource.country(), resource.phone());
    }
}