package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;

import java.util.List;
import java.util.UUID;

public record CooperativeResource(
        UUID cooperativeId,
        String cooperativeName,
        List<MemberSummaryResource> members,
        List<AdministratorSummaryResource> administrators
) {
}

