package com.github.basespring.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DuplicateEntryUserValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateEntryUserConstraint {
    String message() default "data telah ada";

    String columnName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}