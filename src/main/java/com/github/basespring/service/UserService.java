package com.github.basespring.service;

import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.database.dao.jdbc.User;

import java.io.IOException;

public interface UserService {

    ServiceResolver<User> register(RegisterRequest request) throws IOException;
}
