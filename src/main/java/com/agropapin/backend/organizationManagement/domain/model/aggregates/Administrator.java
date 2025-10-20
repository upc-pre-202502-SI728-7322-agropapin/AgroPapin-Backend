package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class Administrator extends AuditableAbstractAggregateRoot<Administrator> {

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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
