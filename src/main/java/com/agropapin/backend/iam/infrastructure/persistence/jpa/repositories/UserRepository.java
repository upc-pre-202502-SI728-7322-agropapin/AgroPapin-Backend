package com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String email);
    boolean existsByUsername(String email);
}
