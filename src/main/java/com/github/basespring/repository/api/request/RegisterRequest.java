package com.github.basespring.repository.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterRequest {
    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private MultipartFile profilePicture;
}
