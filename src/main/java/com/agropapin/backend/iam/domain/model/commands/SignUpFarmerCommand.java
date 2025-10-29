package com.agropapin.backend.iam.domain.model.commands;



public record SignUpFarmerCommand(
        String email,
        String password ,
        String firstName,
        String lastName,
        String country,
        String phone
) {
}