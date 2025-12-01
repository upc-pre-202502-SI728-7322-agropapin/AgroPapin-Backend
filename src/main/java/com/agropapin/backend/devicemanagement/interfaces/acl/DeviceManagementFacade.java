package com.agropapin.backend.devicemanagement.interfaces.acl;

import com.agropapin.backend.devicemanagement.application.internal.queryservices.DeviceQueryServiceImpl;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetActuatorByIdQuery;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceManagementFacade {
    private final DeviceQueryServiceImpl deviceQueryService;

    public DeviceManagementFacade(DeviceQueryServiceImpl deviceQueryService) {
        this.deviceQueryService = deviceQueryService;
    }

    public UUID getPlotIdByActuatorId(UUID actuatorId) {
        var getActuatorQuery = new GetActuatorByIdQuery(actuatorId);
        var actuator = deviceQueryService.handle(getActuatorQuery);

        if (actuator.isPresent()) {
            return actuator.get().plotId();
        } else {
            throw new RuntimeException("Actuator not found");
        }
    }
}
