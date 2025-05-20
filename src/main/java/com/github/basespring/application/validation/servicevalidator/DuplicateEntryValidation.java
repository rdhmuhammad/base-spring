package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DuplicateEntryValidation<T, R extends BaseRepository<?, ?>> extends ValidationStep<T> {

    private R repo;

    private String columnName;

    @Override
    public ValidationResult validate(T input) {

        return null;
    }

}
