package com.github.basespring.application.validation;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class DuplicateEntryConstraint
        implements ConstraintValidator<DuplicateEntry, String> {

    private String columns;

    private final List<Module> modules = new ArrayList<>();

    public void addModule(Class<? extends  BaseEntity> entity, BaseRepository<?, ?> repository, Class<?> dto) {
        this.modules.add(new Module(entity, repository, dto));
    }

    public DuplicateEntryConstraint() {
    }

    @Override
    public void initialize(DuplicateEntry constraint) {
        try {
            Method columnName = constraint.annotationType().getMethod("columnName");
            Object invoke = columnName.invoke(constraint);
            String cols = String.valueOf(invoke);
            if (!cols.isEmpty()) columns = cols;
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

    public boolean isValid(String value, ConstraintValidatorContext context) {
        Class<?> containingClass = getContainingClass(context);
        for (Module module : this.modules) {
            if (module.getDtoClass() == containingClass) {
                boolean exists = module.getRepo()
                        .exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columns.isEmpty() ? getFieldName(context): columns), value));
                return !exists;
            }
        }

        log.error("Invalid class type => no class {} found to validate", containingClass.getName());

        return false;
    }
}
