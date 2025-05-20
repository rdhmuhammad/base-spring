package com.github.basespring.application.app;

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
                .orElseGet(()-> {
                    log.warn("config for key => {} not found", key);
                    return new Config();
                })
                .getValue();

    }

    @Autowired
    private RequestClassLoader requestClassLoader;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    protected <R extends BaseRepository<?,?>, T> ValidationResult validateDuplication(R repo, T request, String ...column) {
        if (column.length == 0) {
            return new AllColDuplicateEntryValidation<>(repo, requestClassLoader, r2dbcEntityTemplate)
                    .validate(request);
        }
        DuplicateEntryValidation<T, R> validation = new DuplicateEntryValidation<>(repo, column[0]);
        for (String key : column) {
            validation.addNext(new DuplicateEntryValidation<>(repo, key));
        }

       return validation.validate(request);
    }

}
