package com.agropapin.backend.organizationManagement.domain.services;

import java.util.Optional;

public interface EnterpriseCommandService {
    Optional<Enterprise> handle(UpdateEnterpriseCommand command);

}
