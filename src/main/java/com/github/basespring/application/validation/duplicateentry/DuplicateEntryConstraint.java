package com.github.basespring.application.validation.duplicateentry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class DuplicateEntryConstraint
        implements ConstraintValidator<DuplicateEntry, String> {


    private String columns;

    @Autowired
    private DuplicateEntryModule modules;

    private Class<?> constraintClass;

    public DuplicateEntryConstraint() {
    }

    @Override
    public void initialize(DuplicateEntry constraint) {
        try {
            Method columnName = constraint.annotationType().getMethod("columnName");
            Object invoke = columnName.invoke(constraint);
            String cols = String.valueOf(invoke);
            if (!cols.isEmpty()) columns = cols;

            Method request = constraint.annotationType().getMethod("constraint");
            Object invokeRequest = request.invoke(constraint);
            constraintClass = (Class<?>) invokeRequest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFieldName(ConstraintValidatorContext context) {
        // Get the last node of the path (which is the field name)
        Object propertyPath = context.unwrap(ConstraintDescriptor.class)
                .getAttributes()
                .get("propertyPath");

        return Objects.nonNull(propertyPath) ? propertyPath.toString() : "unknown";
    }

    private Class<?> getContainingClass(ConstraintValidatorContext context) {
        try {
            // Unwrap the root bean class
            return context.unwrap(ConstraintValidatorContext.ConstraintViolationBuilder.class)
                    .getClass()
                    .getEnclosingClass();
        } catch (Exception e) {
            return null;
        }
    }

    @Async
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (DuplicateEntryModule.Item module : modules.getItems()) {
            if (module.getDtoClass() == constraintClass) {
                boolean exists = module.getRepo()
                        .exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columns.isEmpty() ? getFieldName(context): columns), value));
                return !exists;
            }
        }

        log.error("Invalid class type => no class {} found to validate", constraintClass.getName());

        return false;
    }
}
