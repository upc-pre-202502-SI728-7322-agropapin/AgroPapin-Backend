package com.agropapin.backend.iam.domain.model.queries;


import com.fromzero.backend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
