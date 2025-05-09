package com.github.basespring.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DuplicateEntryConstraint.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateEntry {
    String message() default "data telah ada";

    String columnName();

    Class<?> constraint();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}