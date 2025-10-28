package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {
    Optional<Farmer> findFarmerByUserId(UUID id);
    boolean existsFarmerByUserId(UUID userId);
}
