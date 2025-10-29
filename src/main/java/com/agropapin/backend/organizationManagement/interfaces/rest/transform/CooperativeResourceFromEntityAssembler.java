package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.CooperativeResource;

public class CooperativeResourceFromEntityAssembler {
    public static CooperativeResource toResourceFromEntity(Cooperative entity) {
        return new CooperativeResource(
                entity.getId(),
                entity.getCooperativeName(),
                entity.getMembers(),
                entity.getAdministrators()
        );
    }
}
