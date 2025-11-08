package com.agropapin.backend.devicemanagement.domain.model.aggregates;

import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;
import com.agropapin.backend.devicemanagement.domain.model.enums.SensorType;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.BatteryLevel;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.DeviceModel;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Sensor extends AuditableAbstractAggregateRoot<Sensor> {

    @Getter
    private String serialNumber;

    @Getter
    private UUID plotId;

    @Getter
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @Getter
    private Instant lastSeen;

    @Getter
    @Embedded
    private BatteryLevel batteryLevel;

    @Getter
    @Embedded
    private DeviceModel deviceModel;

    @Getter
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    public Sensor(String serialNumber, UUID plotId, DeviceModel deviceModel, SensorType sensorType) {
        this.serialNumber = serialNumber;
        this.plotId = plotId;
        this.deviceModel = deviceModel;
        this.sensorType = sensorType;
        this.status = DeviceStatus.PROVISIONED;
        this.lastSeen = Instant.now();
        this.batteryLevel = new BatteryLevel(100.0f);
    }

    public Sensor() {
    }

    public void updateStatus(DeviceStatus newStatus) {
        this.status = newStatus;
        this.lastSeen = Instant.now();
    }

    public void updateBatteryLevel(BatteryLevel newBatteryLevel) {
        this.batteryLevel = newBatteryLevel;
        this.lastSeen = Instant.now();
    }
}
