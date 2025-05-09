package com.github.basespring.controller;

import com.github.basespring.application.app.AppController;
import com.github.basespring.application.base.BaseEntity;
import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.api.response.RegisterResponse;
import com.github.basespring.repository.database.dao.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/api/v1/user")
public class UserController extends AppController {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        ServiceResolver<User> resolver = userService.register(request);
        return responseConvertDetail(resolver, RegisterResponse.class);
    }
}
