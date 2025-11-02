package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CooperativeRepository extends JpaRepository<Cooperative, UUID> {
    boolean existsByIdAndAdministrators_Id(UUID cooperativeId, UUID administratorId);
    Optional<Cooperative> findByAdministrators_UserIdOrMembers_UserId(String userId, String userId2);
    Optional<Cooperative> findByAdministrators_UserId(String userId);
    boolean existsByIdAndAdministrators_UserId(UUID cooperativeId, String userId);
}
