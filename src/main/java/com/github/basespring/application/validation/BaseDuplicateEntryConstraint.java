package com.github.basespring.application.validation;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseDuplicateEntryConstraint<
        T extends BaseEntity,
        Ano extends Annotation,
        Repo extends JpaSpecificationExecutor<T>> implements ConstraintValidator<Ano, String> {

    private Repo repo;

    private DuplicateEntrySpec<T> spec;

    private String columns;

    public void build(Repo repo) {
        this.repo = repo;
        this.spec = new DuplicateEntrySpec<T>();
    }

    @Override
    public void initialize(Ano constraint) {
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

    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean exists = repo.exists(spec.filterDuplicate(value, columns.isEmpty() ? getFieldName(context): columns));
        return !exists;
    }
}
