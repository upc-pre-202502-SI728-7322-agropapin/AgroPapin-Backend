package com.agropapin.backend.iam.interfaces.rest.resources;

public record SignUpAdministratorResource(
        String email,
        String password ,
        String firstName,
        String lastName,
        String country,
        String phone) {
}