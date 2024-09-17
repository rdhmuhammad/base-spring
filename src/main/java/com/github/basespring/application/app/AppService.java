package com.github.basespring.application.app;

import com.github.basespring.application.base.BaseService;
import com.github.basespring.application.constant.AppConstants;

public class AppService extends BaseService {

    public AppService() {
        setSuccess(AppConstants.Message.DEFAULT_SUCCESS);
        setError(AppConstants.Message.DEFAULT_ERROR);
    }

}
