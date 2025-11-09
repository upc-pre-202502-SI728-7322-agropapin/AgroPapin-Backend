package com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    Optional<Sensor> findByPlotId(UUID plotId);
    Optional<Sensor> findSensorByIdAndPlotId(UUID sensorId, UUID plotId);
}
