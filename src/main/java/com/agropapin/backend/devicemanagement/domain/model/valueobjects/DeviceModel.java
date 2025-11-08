package com.agropapin.backend.devicemanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record DeviceModel(
        String model,
        String version
) {
    public DeviceModel() {
        this(null, null);
    }
}
