package com.agropapin.backend.iam.interfaces.rest.resources;

public record SignUpEnterpriseResource(
        String username,
        String password,
        String enterpriseName
        ) {
}