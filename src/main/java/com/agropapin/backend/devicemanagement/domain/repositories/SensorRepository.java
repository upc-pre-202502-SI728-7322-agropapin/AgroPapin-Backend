package com.agropapin.backend.devicemanagement.domain.repositories;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
}
