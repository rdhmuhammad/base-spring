package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class AllColDuplicateEntryValidation<T, R extends BaseRepository<?, ?>> extends ValidationStep<T> {

    private R repo;

    private RequestClassLoader requestClassLoader;

    private R2dbcEntityTemplate dbTemplate;

    @Override
    public ValidationResult validate(T input) {
        HashMap<String, Boolean> results = new HashMap<>();
        
        List<String> byClass = requestClassLoader.findByClass(input.getClass());
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(input);



        boolean exists = repo.exists((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String column : byClass) {
                predicates.add(criteriaBuilder.equal(root.get(column), beanWrapper.getPropertyValue(column)));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
        if (exists) {
            return ValidationResult.invalid("Value");
        }
        return ValidationResult.valid();
    }

}
