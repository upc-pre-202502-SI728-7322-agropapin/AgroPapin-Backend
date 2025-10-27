package com.agropapin.backend.iam.domain.model.commands;

import com.agropapin.backend.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String email, String password, List<Role> roles) {
}