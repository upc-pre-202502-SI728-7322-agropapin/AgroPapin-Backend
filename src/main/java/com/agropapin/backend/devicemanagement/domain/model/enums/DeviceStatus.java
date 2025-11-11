package com.agropapin.backend.devicemanagement.domain.model.enums;

public enum DeviceStatus {
    ONLINE,
    OFFLINE,
    MAINTENANCE,
    PROVISIONED;

    public boolean canTransitionTo(DeviceStatus newStatus) {
        return switch (this) {
            case ONLINE, PROVISIONED -> newStatus == OFFLINE || newStatus == MAINTENANCE;
            case OFFLINE -> newStatus == ONLINE || newStatus == MAINTENANCE;
            case MAINTENANCE -> newStatus == ONLINE || newStatus == OFFLINE || newStatus == PROVISIONED;
        };
    }
}
