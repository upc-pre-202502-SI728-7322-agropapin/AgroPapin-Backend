package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plot extends AuditableAbstractAggregateRoot<Plot> {

    @Column(name = "plot_name", nullable = false, length = 100)
    @NotBlank(message = "Plot name is mandatory")
    @Size(max = 100, message = "Plot name must not exceed 100 characters")
    private String plotName;

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Area is mandatory")
    @Positive(message = "Area must be positive")
    @Digits(integer = 8, fraction = 2, message = "Area must have valid decimal format")
    private BigDecimal area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    @NotNull(message = "Field is mandatory")
    private Field field;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Status is mandatory")
    private PlotStatus status;
}
