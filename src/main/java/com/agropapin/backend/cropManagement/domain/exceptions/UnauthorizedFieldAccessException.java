package com.agropapin.backend.cropManagement.domain.exceptions;

public class UnauthorizedFieldAccessException extends RuntimeException {
    private final Long fieldId;
    private final String attemptedUserId;

    public UnauthorizedFieldAccessException(Long fieldId, String attemptedUserId) {
        super(String.format(
                "User '%s' is not authorized to access field with id '%s'",
                attemptedUserId, fieldId
        ));
        this.fieldId = fieldId;
        this.attemptedUserId = attemptedUserId;
    }

    public UnauthorizedFieldAccessException(String message) {
        super(message);
        this.fieldId = null;
        this.attemptedUserId = null;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public String getAttemptedUserId() {
        return attemptedUserId;
    }
}
