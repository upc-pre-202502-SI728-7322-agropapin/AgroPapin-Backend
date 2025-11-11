package com.agropapin.backend.devicemanagement.interfaces.rest.resources;

import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDeviceStatusResource(
        @NotNull(message = "Actuator status is required")
        DeviceStatus status
) {
}
