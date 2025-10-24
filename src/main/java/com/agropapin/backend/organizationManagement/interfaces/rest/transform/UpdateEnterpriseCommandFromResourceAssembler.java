package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateEnterpriseResource;

public class UpdateEnterpriseCommandFromResourceAssembler {
    public static UpdateEnterpriseCommand toCommandFromResource(Long enterpriseId, UpdateEnterpriseResource resource) {
        return new UpdateEnterpriseCommand(
                enterpriseId,
                resource.enterpriseName(),
                resource.description(),
                resource.country(),
                resource.ruc(),
                resource.phone(),
                resource.website(),
                resource.profileImgUrl(),
                resource.sector());
    }
}
