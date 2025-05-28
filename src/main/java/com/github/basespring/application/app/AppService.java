package com.github.basespring.application.app;

import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.BaseRepository;
import com.github.basespring.application.base.BaseService;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.application.validation.servicevalidator.*;
import com.github.basespring.repository.database.dao.jdbc.Config;
import com.github.basespring.repository.database.repo.jdbc.ConfigRepository;
import com.github.basespring.repository.database.repo.jdbc.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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

    protected <E extends BaseEntity, T> ValidationStep<T> validateDuplication(Class<E> entity) {
        return new AllColDuplicateEntryValidation<>(entity, entityManager, requestClassLoader);
    }

}
