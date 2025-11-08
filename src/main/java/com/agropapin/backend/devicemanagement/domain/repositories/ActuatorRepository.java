package com.agropapin.backend.devicemanagement.domain.repositories;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActuatorRepository extends JpaRepository<Actuator, UUID> {
}
