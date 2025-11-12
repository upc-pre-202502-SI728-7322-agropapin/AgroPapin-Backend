package com.agropapin.backend.cropManagement.domain.model.enums;

public enum FieldStatus {
    ACTIVE,
    INACTIVE,
    UNDER_MAINTENANCE;

    public boolean canTransitionTo(FieldStatus newStatus) {
        return switch (this) {
            case ACTIVE -> newStatus == INACTIVE || newStatus == UNDER_MAINTENANCE;
            case INACTIVE -> newStatus == ACTIVE || newStatus == UNDER_MAINTENANCE;
            case UNDER_MAINTENANCE -> newStatus == ACTIVE || newStatus == INACTIVE;
        };
    }
}
