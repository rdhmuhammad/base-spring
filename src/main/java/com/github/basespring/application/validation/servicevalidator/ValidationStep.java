package com.github.basespring.application.validation.servicevalidator;

public abstract class ValidationStep<T> {

    private ValidationStep<T> next;

    public ValidationStep<T> addNext(ValidationStep<T> next) {
        if (this.next == null) {
            this.next = next;
            return this;
        }

        ValidationStep<T> lastNext = this.next;
        while (lastNext.next != null) {
            lastNext = lastNext.next;
        }
        lastNext.next = next;
        return this;
    }

    public ValidationResult getResult(T input) {
        ValidationResult validate = this.validate(input);
        if (validate.isInvalid()) return validate;
        if (this.next == null) {
            return validate;
        }
        ValidationStep<T> lastNext = this.next;
        while (lastNext != null) {
            validate = lastNext.validate(input);
            if (validate.isInvalid()) return validate;
            lastNext = lastNext.next;
        }

        return validate;
    }


    protected abstract ValidationResult validate(T input);
}
