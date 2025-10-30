package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import java.util.UUID;

public record MemberSummaryResource(
        UUID id,
        String firstName,
        String lastName,
        String country,
        String phone,
        UUID userId
){}
