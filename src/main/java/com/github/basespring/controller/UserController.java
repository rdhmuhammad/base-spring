package com.github.basespring.controller;

import com.github.basespring.application.app.AppController;
import com.github.basespring.application.base.ServiceResolver;
import com.github.basespring.repository.api.request.RegisterRequest;
import com.github.basespring.repository.api.response.RegisterResponse;
import com.github.basespring.repository.database.dao.jdbc.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends AppController {

    @PostMapping(value = "/register", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
    })
    public ResponseEntity<?> register(@ModelAttribute @Valid RegisterRequest request) throws IOException {
        ServiceResolver<User> resolver = userService.register(request);
        return responseConvertDetail(resolver, RegisterResponse.class);
    }
}
