package com.github.basespring.application.validation.servicevalidator;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class DuplicateEntryValidation<T, R extends BaseRepository<?, ?>> extends ValidationStep<T> {

    @Override
    public ValidationResult validate(T input) {
        log.info("validate duplicate entry ");
        return ValidationResult.valid();
    }

}
