package com.github.basespring.application.app;

import com.github.basespring.application.base.*;
import com.github.basespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppController extends BaseResController {

    @Autowired
    protected UserService userService;
}