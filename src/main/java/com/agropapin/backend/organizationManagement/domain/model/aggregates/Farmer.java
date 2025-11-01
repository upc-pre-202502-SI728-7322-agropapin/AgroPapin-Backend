package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Farmer extends AuditableAbstractAggregateRoot<Farmer> {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "country")
    private String country = "No country provided.";

    @Column(name = "phone")
    private String phone = "999 999 999";

    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id")
    private Cooperative cooperative;

    public Farmer(String email, String userId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.firstName = firstName != null ? firstName : "No first name provided.";
        this.lastName = lastName != null ? lastName : "No last name provided.";
        this.country = country != null ? country : "No country provided.";
        this.phone = phone != null ? phone : "999 999 999";
        this.userId = userId;
    }



    public void updatePersonalInfo(String firstName, String lastName,
                                   String country, String phone) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.phone = phone;
    }

    public void assignToCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    public void clearCooperative() {
        this.cooperative = null;
    }
}
