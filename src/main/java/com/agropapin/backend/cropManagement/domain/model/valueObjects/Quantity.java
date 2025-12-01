package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Quantity {

    private double amount;
    private String unit;

    public Quantity(double amount, String unit) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit cannot be empty.");
        }
        this.amount = amount;
        this.unit = unit;
    }
}
