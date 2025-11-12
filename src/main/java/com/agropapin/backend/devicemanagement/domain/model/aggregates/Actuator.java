package com.agropapin.backend.devicemanagement.domain.model.aggregates;

import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorState;
import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorType;
import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.DeviceModel;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Actuator extends AuditableAbstractAggregateRoot<Actuator> {

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(name = "plot_id", nullable = false, unique = true)
    private UUID plotId;

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state", nullable = false)
    private ActuatorState currentState;

    @Embedded
    private DeviceModel deviceModel;

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
        if (this.status == newStatus) {
            return;
        }

        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("Invalid status transition from %s to %s", this.status, newStatus)
            );
        }

        DeviceStatus oldStatus = this.status;
        this.status = newStatus;
    }
}
