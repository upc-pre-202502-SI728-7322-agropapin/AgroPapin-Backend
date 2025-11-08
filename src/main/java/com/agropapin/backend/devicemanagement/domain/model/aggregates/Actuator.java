package com.agropapin.backend.devicemanagement.domain.model.aggregates;

import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorState;
import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorType;
import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.DeviceModel;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Actuator extends AuditableAbstractAggregateRoot<Actuator> {

    @Getter
    private String serialNumber;

    @Getter
    private UUID plotId;

    @Getter
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @Getter
    @Enumerated(EnumType.STRING)
    private ActuatorState currentState;

    @Getter
    @Embedded
    private DeviceModel deviceModel;

    @Getter
    @Enumerated(EnumType.STRING)
    private ActuatorType actuatorType;

    public Actuator(String serialNumber, UUID plotId, DeviceModel deviceModel, ActuatorType actuatorType) {
        this.serialNumber = serialNumber;
        this.plotId = plotId;
        this.deviceModel = deviceModel;
        this.actuatorType = actuatorType;
        this.status = DeviceStatus.PROVISIONED;
        this.currentState = ActuatorState.IDLE;
    }

    public Actuator() {
    }

    public void changeState(ActuatorState newState) {
        if (this.status == DeviceStatus.OFFLINE) {
            throw new IllegalStateException("Cannot change state of an offline actuator");
        }
        this.currentState = newState;
    }

    public void updateStatus(DeviceStatus newStatus) {
        this.status = newStatus;
    }
}
