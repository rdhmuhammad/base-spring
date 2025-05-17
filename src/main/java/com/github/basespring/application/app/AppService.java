package com.github.basespring.application.app;

import com.github.basespring.application.base.BaseService;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.application.exceptions.ConfigNotFoundError;
import com.github.basespring.repository.database.dao.Config;
import com.github.basespring.repository.database.repo.ConfigRepository;
import com.github.basespring.repository.database.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

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

}
