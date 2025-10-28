package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;

import java.util.List;

public record CooperativeResource(
        Long cooperativeId,
        String cooperativeName,
        List<Farmer> members,
        List<Administrator> administrators
) {
}
