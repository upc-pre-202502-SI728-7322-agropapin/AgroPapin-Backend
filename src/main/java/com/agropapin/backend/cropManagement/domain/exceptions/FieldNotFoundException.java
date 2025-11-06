package com.agropapin.backend.cropManagement.domain.exceptions;

import java.util.UUID;

public class FieldNotFoundException extends RuntimeException{
    private final UUID fieldId;

    public FieldNotFoundException(UUID fieldId) {
        super(String.format("Field with id '%s' was not found", fieldId));
        this.fieldId = fieldId;
    }

    public FieldNotFoundException(UUID fieldId, Throwable cause) {
        super(String.format("Field with id '%s' was not found", fieldId), cause);
        this.fieldId = fieldId;
    }

    public UUID getFieldId() {
        return fieldId;
    }
}
