package com.agropapin.backend.devicemanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BatteryLevel(Float level) {
    public BatteryLevel {
        if (level < 0.0f || level > 100.0f) {
            throw new IllegalArgumentException("Battery level must be between 0.0 and 100.0");
        }
    }

    public BatteryLevel() {
        this(0.0f);
    }
}
