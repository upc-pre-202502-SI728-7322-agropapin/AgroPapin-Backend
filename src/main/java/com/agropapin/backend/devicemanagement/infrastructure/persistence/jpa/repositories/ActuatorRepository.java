package com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActuatorRepository extends JpaRepository<Actuator, UUID> {
    Optional<Actuator> findActuatorById(UUID actuatorId);
    Optional<List<Actuator>> findAllByPlotId(UUID plotId);
    Optional<Actuator> findActuatorByIdAndPlotId(UUID actuatorId, UUID plotId);
}
