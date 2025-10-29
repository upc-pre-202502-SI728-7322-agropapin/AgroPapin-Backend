package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Farmer extends AuditableAbstractAggregateRoot<Farmer> {

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "country", nullable = false)
    private String country = "No country provided.";

    @Column(name = "phone", nullable = false)
    private String phone = "999 999 999";

    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id")
    private Cooperative cooperative;

    public Farmer(String firstName, String lastName, String country,
                  String phone, UUID userId) {
        this.firstName = firstName;
        this.lastName = lastName;
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
