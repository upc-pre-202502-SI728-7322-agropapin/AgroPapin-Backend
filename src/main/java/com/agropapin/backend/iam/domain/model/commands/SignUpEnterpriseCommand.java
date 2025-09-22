package com.agropapin.backend.iam.domain.model.commands;



public record SignUpEnterpriseCommand(
        String username ,
        String password,
        String enterpriseName
) {
}
