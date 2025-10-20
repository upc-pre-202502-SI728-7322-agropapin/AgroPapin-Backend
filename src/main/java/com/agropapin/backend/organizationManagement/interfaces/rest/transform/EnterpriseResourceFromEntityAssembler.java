package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Enterprise;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.EnterpriseResource;

public class EnterpriseResourceFromEntityAssembler {
    public static EnterpriseResource toResourceFromEntity(Enterprise entity) {
        return new EnterpriseResource(
                entity.getId(),
                entity.getEnterpriseName(),
                entity.getDescription(),
                entity.getCountry(),
                entity.getRuc(),
                entity.getPhone(),
                entity.getWebsite(),
                entity.getProfileImgUrl(),
                entity.getSector(),
                entity.getUser().getId());
    }
}
