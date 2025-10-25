package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findAdministratorByUser_Id(Long userId);
}
