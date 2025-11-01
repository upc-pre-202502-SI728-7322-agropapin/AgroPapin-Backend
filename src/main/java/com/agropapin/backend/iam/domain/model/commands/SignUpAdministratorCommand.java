package com.agropapin.backend.iam.domain.model.commands;



public record SignUpAdministratorCommand(
        String email,
        String userId
) {
}
