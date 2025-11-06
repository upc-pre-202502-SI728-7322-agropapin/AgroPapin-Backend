package com.agropapin.backend.cropManagement.domain.model.valueObjects;

public enum PlotStatus {
    EMPTY,
    PLANTED,
    HARVESTED;

    public boolean canTransitionTo(PlotStatus newStatus) {
        return switch (this) {
            case EMPTY -> newStatus == PLANTED || newStatus == HARVESTED;
            case PLANTED -> newStatus == EMPTY || newStatus == HARVESTED;
            case HARVESTED -> newStatus == EMPTY || newStatus == PLANTED;
        };
    }
}
