package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.AdministratorSummaryResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.CooperativeResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.MemberSummaryResource;

import java.util.stream.Collectors;

public class CooperativeResourceFromEntityAssembler {
    public static CooperativeResource toResourceFromEntity(Cooperative cooperative) {
        return new CooperativeResource(
                cooperative.getId(),
                cooperative.getCooperativeName(),
                cooperative.getMembers().stream()
                        .map(member -> new MemberSummaryResource(
                                member.getId(),
                                member.getFirstName(),
                                member.getLastName(),
                                member.getCountry(),
                                member.getPhone(),
                                member.getUserId()
                        )).collect(Collectors.toList()),
                cooperative.getAdministrators().stream()
                        .map(admin -> new AdministratorSummaryResource(
                                admin.getId(),
                                admin.getFirstName(),
                                admin.getLastName(),
                                admin.getCountry(),
                                admin.getPhone(),
                                admin.getUserId()
                        )).collect(Collectors.toList())
        );
    }
}
