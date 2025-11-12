package com.agropapin.backend.cropManagement.domain.model.enums;

public enum CropStatus {
    GROWING,
    HARVESTED,
    FAILED;

    public boolean canTransitionTo(CropStatus newStatus) {
        return switch (this) {
            case GROWING -> newStatus == HARVESTED || newStatus == FAILED;
            case HARVESTED -> newStatus == GROWING || newStatus == FAILED;
            case FAILED -> newStatus == GROWING || newStatus == HARVESTED;
        };
    }
}
