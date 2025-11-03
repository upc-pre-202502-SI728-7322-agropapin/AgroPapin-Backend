package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.FieldStatus;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Field extends AuditableAbstractAggregateRoot<Field> {
    @Column(name = "field_name", nullable = false, length = 100)
    @NotBlank(message = "Field name is mandatory")
    @Size(max = 100, message = "Field name must not exceed 100 characters")
    private String fieldName;

    @Column(name = "location", nullable = false, length = 200)
    @NotBlank(message = "Location is mandatory")
    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    @Column(name = "total_area", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Total area is mandatory")
    @Positive(message = "Total area must be positive")
    @Digits(integer = 8, fraction = 2, message = "Total area must have valid decimal format")
    private BigDecimal totalArea;

    @Column(name = "farmer_user_id",unique = true, updatable = false, nullable = false)
    @NotNull(message = "User ID is mandatory")
    private String farmerUserId;

    @OneToMany(
            mappedBy = "field",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Plot> plots = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Status is mandatory")
    private FieldStatus status;
}
