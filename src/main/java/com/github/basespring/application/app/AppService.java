package com.github.basespring.application.app;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.application.base.BaseService;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.application.validation.servicevalidator.AllColDuplicateEntryValidation;
import com.github.basespring.application.validation.servicevalidator.DuplicateEntryValidation;
import com.github.basespring.application.validation.servicevalidator.RequestClassLoader;
import com.github.basespring.application.validation.servicevalidator.ValidationResult;
import com.github.basespring.repository.database.dao.jdbc.Config;
import com.github.basespring.repository.database.repo.jdbc.ConfigRepository;
import com.github.basespring.repository.database.repo.jdbc.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import javax.persistence.EntityManager;

@Slf4j
public class AppService extends BaseService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ConfigRepository configRepository;

    public AppService() {
        setSuccess(AppConstants.Message.DEFAULT_SUCCESS);
        setError(AppConstants.Message.DEFAULT_ERROR);
    }

    protected String config(String key) {
        return configRepository.findFirstByKey(key)
                .orElseGet(() -> {
                    log.warn("config for key => {} not found", key);
                    return new Config();
                })
                .getValue();

    }

    @Autowired
    private RequestClassLoader requestClassLoader;

    @Autowired
    private EntityManager entityManager;

    protected <R extends BaseRepository<?, ?>, T> ValidationResult validateDuplication(R repo, T request, String... columns) {
        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException("columns must not be null or empty");
        }
        DuplicateEntryValidation<T, R> validation = new DuplicateEntryValidation<>(repo, columns[0]);
        for (String key : columns) {
            validation.addNext(new DuplicateEntryValidation<>(repo, key));
        }
        return validation.validate(request);
    }

    protected <E extends BaseEntity, T> ValidationResult validateDuplication(Class<E> entity, T request) {
        return new AllColDuplicateEntryValidation<>(entity, entityManager, requestClassLoader)
                .validate(request);

    }

}
