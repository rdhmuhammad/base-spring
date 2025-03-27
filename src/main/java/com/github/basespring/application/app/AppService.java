package com.github.basespring.application.app;

import com.github.basespring.application.base.BaseService;
import com.github.basespring.application.constant.AppConstants;
import com.github.basespring.repository.database.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

public class AppService extends BaseService {

    @Autowired
    protected UserRepository userRepository;

    public AppService() {
        setSuccess(AppConstants.Message.DEFAULT_SUCCESS);
        setError(AppConstants.Message.DEFAULT_ERROR);
    }

}
