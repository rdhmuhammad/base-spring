package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseRepository;

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

    public abstract ValidationResult validate(T input);

    protected ValidationResult validateNext(T input) {
        if (next == null) {
            return ValidationResult.valid();
        }

        return next.validate(input);
    }
}
