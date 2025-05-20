package com.github.basespring.application.validation.servicevalidator;

import lombok.Data;

@Data
public class ValidationResult {
    private final Boolean isValid;

    private final String message;

    public ValidationResult(Boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, message);
    }

}
